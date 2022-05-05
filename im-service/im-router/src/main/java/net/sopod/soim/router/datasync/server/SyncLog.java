package net.sopod.soim.router.datasync.server;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.experimental.Accessors;
import net.sopod.soim.common.util.Jackson;
import org.apache.dubbo.common.io.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * SyncLog
 * syncLog serialize
 *
 * @author tmy
 * @date 2022-05-05 11:37
 */
@Data
@Accessors(chain = true)
public class SyncLog implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(SyncLog.class);

    private static final long serialVersionUID = -8925525709590423526L;

    private static final short MAGIC = 0x7a21;

    public static final int OPT_ADD = 1;
    public static final int OPT_REMOVE = 2;
    public static final int OPT_UPDATE = 3;

    /** 日志序列号保证顺序 */
    private int logSeq;

    /**
     * 操作类型:
     *   1.新增
     *   2.删除
     *   3.更新
     */
    private int operateType;

    /**
     * {@link SyncTypes} ordinal
     */
    private int syncDataType;

    /** 数据id标示 */
    private String dataKey;

    /** ================ 新增参数:序列化后的数据 ===================== */
    private String addSerializeData;

    /** ================ 更新参数 ===================== */
    private String clazz;

    private String method;

    private Object[] args;

    public byte[] toBytes() {
        byte[] clazzBytes = clazz.getBytes();
        byte[] methodBytes = method.getBytes();
        int argSize = args == null ? 0 : args.length;

        byte[][] byteArgs = new byte[argSize][];
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                // 反序列化时根据方法参数类型json反序列化
                // TODO null
                String argJson = Jackson.json().serialize(args[i]);
                byteArgs[i] = argJson.getBytes(StandardCharsets.UTF_8);
            }
        }
        int argsByteLen = 0;
        for (byte[] byteArg : byteArgs) {
            argsByteLen += 4;
            argsByteLen += byteArg.length;
        }
        // 总长 + 同步数据类型(byte) +
        byte[] bytes = new byte[
                2   // 魔术
                + 4 // 请求体总长度
                + 1 // 同步数据类型
                + 4 // clazz 字节长度
                + clazzBytes.length // clazz字节
                + 4 // method 字节长度
                + methodBytes.length // method 字节
                + 4 // args参数个数
                + argsByteLen // args参数字节
        ];
        int offset = 0;
        Bytes.short2bytes(MAGIC, bytes, offset);
        offset += 2;

        Bytes.int2bytes(bytes.length - 6, bytes, offset);
        offset += 4;

        bytes[offset] = (byte)syncDataType;
        offset += 1;

        Bytes.int2bytes(clazzBytes.length, bytes, offset);
        offset += 4;
        System.arraycopy(clazzBytes, 0, bytes, offset, clazzBytes.length);
        offset += clazzBytes.length;

        Bytes.int2bytes(methodBytes.length, bytes, offset);
        offset += 4;
        System.arraycopy(methodBytes, 0, bytes, offset, methodBytes.length);
        offset += methodBytes.length;

        Bytes.int2bytes(argSize, bytes, offset);
        offset += 4;
        if (argsByteLen > 0) {
            for (byte[] byteArg : byteArgs) {
                Bytes.int2bytes(byteArg.length, bytes, offset);
                offset += 4;
                System.arraycopy(byteArg, 0, bytes, offset, byteArg.length);
                offset += byteArg.length;
            }
        }
        return bytes;
    }

    public static SyncLog read(ByteBuf buf) {
        short magic = buf.readShort();
        if (magic != MAGIC) {
            throw new IllegalStateException("unknown bytes magic error");
        }
        SyncLog log = new SyncLog();
        // 后续bytes长度
        int dataLen = buf.readInt();
        log.syncDataType = buf.readByte();
        int clazzLen = buf.readInt();
        byte[] clazzBytes = new byte[clazzLen];
        buf.readBytes(clazzBytes);
        log.clazz = new String(clazzBytes, StandardCharsets.UTF_8);
        int methodLen = buf.readInt();
        byte[] methodBytes = clazzLen >= methodLen ? clazzBytes : new byte[methodLen];
        buf.readBytes(methodBytes, 0, methodLen);
        log.method = new String(methodBytes, 0, methodLen, StandardCharsets.UTF_8);
        int argSize = buf.readInt();
        log.args = new Object[argSize];
        if (argSize > 0) {
            Method method = getClassMethod(log.clazz, log.method);
            if (method == null) {
                throw new IllegalStateException("类" + log.clazz + "方法" + log.method + "未找到");
            }
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != argSize) {
                throw new IllegalStateException("类" + log.clazz + "方法" + log.method + "指定参数" + argSize + "个,查到参数" + paramTypes.length + "个");
            }
            // 复用 bytes
            byte[] argBytes = new byte[0];
            for (int i = 0; i < argSize; i++) {
                int argLen = buf.readInt();
                argBytes = argBytes.length >= argLen ? argBytes : new byte[argLen];
                buf.readBytes(argBytes, 0, argLen);
                String argJson = new String(argBytes, 0, argLen, StandardCharsets.UTF_8);
                Object arg = Jackson.json().deserialize(argJson, paramTypes[i]);
                log.args[i] = arg;
            }
        }
        return log;
    }

    /**
     * TODO 缓存反射结果
     */
    @Nullable
    private static Method getClassMethod(String clazzName, String methodName) {
        try {
            Class<?> clazz = Class.forName(clazzName);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    return method;
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error("获取类型方法失败: {}.{}", clazzName, methodName, e);
        }
        return null;
    }

    public static void main(String[] args) {
        new SyncLog()
                .setSyncDataType(SyncTypes.ROUTER_USER.ordinal())
                .setClazz("")
                .setMethod("")
                .setArgs(new Object[]{});

    }

}
