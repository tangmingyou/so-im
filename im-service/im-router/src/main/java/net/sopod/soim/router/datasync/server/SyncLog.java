package net.sopod.soim.router.datasync.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.datasync.DataSync;
import net.sopod.soim.router.datasync.SyncTypes;
import org.apache.dubbo.common.io.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SyncReviseLog
 * 数据修改日志（增删改）
 *
 * @author tmy
 * @date 2022-05-05 11:37
 */
@Getter
@ToString
@Accessors(chain = true)
public class SyncLog implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(SyncLog.class);

    private static final long serialVersionUID = -8925525709590423526L;

    private static final short MAGIC = 0x7a21;

    public static final int OPT_ADD = 1;
    public static final int OPT_REMOVE = 2;
    public static final int OPT_UPDATE = 3;

    public static <T extends DataSync> AddLog<T> addLog(SyncTypes.SyncType<T> syncType) {
        return new AddLog<>(syncType);
    }

    public static <T extends DataSync> UpdateLog<T> updateLog(int logSeq, SyncTypes.SyncType<T> syncType) {
        return new UpdateLog<>(logSeq, syncType);
    }

    public static <T extends DataSync> RemoveLog<T> removeLog(int logSeq, SyncTypes.SyncType<T> syncType) {
        return new RemoveLog<>(logSeq, syncType);
    }

    protected SyncLog(SyncTypes.SyncType<?> syncType) {
        this.syncDataType = syncType.ordinal();
        this.clazz = syncType.dataType().getName();
    }

    /** 日志序列号保证顺序 */
    protected int logSeq;

    /**
     * 操作类型:
     *   1.新增
     *   2.删除
     *   3.更新
     */
    protected int operateType;

    /**
     * {@link SyncTypes} ordinal
     */
    protected int syncDataType;

    /** ================ 数据id标示:删除,更新用 ===================== */
    protected String dataKey;

    /** ================ 更新数据:类,更新方法,更新方法序列化参数(避免修改) ===================== */
    protected String clazz;

    protected String method;

    protected String[] args;

    /** ================ 新增数据:序列化后的数据(避免修改) ===================== */
    protected List<String> serializeDataCollect;

    /** 数据可能同步给多个订阅者，缓存一下 */
    private transient byte[] toBytesCache;

    public byte[] toBytes() {
        if (toBytesCache == null) {
            synchronized (this) {
                if (toBytesCache == null) {
                    toBytesCache = toBytes0();
                }
            }
        }
        return toBytesCache;
    }

    private byte[] toBytes0() {
        byte[] dataKeyBytes = dataKey == null ? new byte[0] : dataKey.getBytes();
        byte[] clazzBytes = clazz == null ? new byte[0] : clazz.getBytes();
        byte[] methodBytes = method == null ? new byte[0] : method.getBytes();
        int argSize = args == null ? 0 : args.length;

        byte[][] byteArgs = new byte[argSize][];
        int argsByteLen = 0;
        if (argSize > 0) {
            for (int i = 0; i < args.length; i++) {
                // 反序列化时根据方法参数类型json反序列化
                byteArgs[i] = args[i].getBytes(StandardCharsets.UTF_8);
                argsByteLen += 4;
                argsByteLen += byteArgs[i].length;
            }
        }

        int dataCollectSize = serializeDataCollect == null ? 0 : serializeDataCollect.size();
        byte[][] byteData = new byte[dataCollectSize][];
        int dataCollectByteLen = 0;
        if (dataCollectSize > 0) {
            int i = 0;
            for (String dataCollect : serializeDataCollect) {
                byteData[i] = dataCollect.getBytes(StandardCharsets.UTF_8);
                dataCollectByteLen += 4;
                dataCollectByteLen += byteData[i].length;
                i++;
            }
        }
        // 总长 + 同步数据类型(byte) +
        byte[] bytes = new byte[
                2   // 魔术
                + 4 // 请求体总长度
                + 1 // 同步数据类型 SyncType
                + 1 // 新增/删除/更新
                + 4 // logSeq 序列号
                + 4 // dataKey 数据主键标识字节长度
                + dataKeyBytes.length // dataKey 数据字节
                + 4 // clazz 字节长度
                + clazzBytes.length // clazz字节
                + 4 // method 字节长度
                + methodBytes.length // method 字节
                + 4 // args参数个数
                + argsByteLen // args参数字节(len,argByte,len,argByte...)
                + 4 // 序列化数据个数
                + dataCollectByteLen // 序列化数据字节(len,dataBytes,len,dataBytes...)
        ];
        int offset = 0;
        Bytes.short2bytes(MAGIC, bytes, offset);
        offset += 2;

        Bytes.int2bytes(bytes.length - 6, bytes, offset);
        offset += 4;

        bytes[offset] = (byte)syncDataType;
        offset += 1;
        bytes[offset] = (byte)operateType;
        offset += 1;

        Bytes.int2bytes(logSeq, bytes, offset);
        offset += 4;

        Bytes.int2bytes(dataKeyBytes.length, bytes, offset);
        offset += 4;
        System.arraycopy(dataKeyBytes, 0, bytes, offset, dataKeyBytes.length);
        offset += dataKeyBytes.length;

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
        if (argSize > 0) {
            for (byte[] byteArg : byteArgs) {
                Bytes.int2bytes(byteArg.length, bytes, offset);
                offset += 4;
                System.arraycopy(byteArg, 0, bytes, offset, byteArg.length);
                offset += byteArg.length;
            }
        }
        Bytes.int2bytes(dataCollectSize, bytes, offset);
        offset += 4;
        if (dataCollectSize > 0) {
            for (byte[] biData : byteData) {
                Bytes.int2bytes(biData.length, bytes, offset);
                offset += 4;
                System.arraycopy(biData, 0, bytes, offset, biData.length);
                offset += biData.length;
            }
        }
        return bytes;
    }

    public static SyncLog read(ByteBuf buf) {
        short magic = buf.readShort();
        if (magic != MAGIC) {
            throw new IllegalStateException("unknown bytes magic error");
        }
        // 后续bytes长度
        int dataLen = buf.readInt();
        int syncDataType = buf.readByte();
        SyncTypes.SyncType<DataSync> syncType = SyncTypes.getSyncType(syncDataType);
        SyncLog log = new SyncLog(syncType);
        log.operateType = buf.readByte();
        log.logSeq = buf.readInt();
        int dataKeyLen = buf.readInt();
        if (dataKeyLen > 0) {
            byte[] dataKeyBytes = new byte[dataKeyLen];
            buf.readBytes(dataKeyBytes);
            log.dataKey = new String(dataKeyBytes, StandardCharsets.UTF_8);
        }
        int clazzLen = buf.readInt();
        byte[] clazzBytes = new byte[clazzLen];
        if (clazzLen > 0) {
            buf.readBytes(clazzBytes);
            log.clazz = new String(clazzBytes, StandardCharsets.UTF_8);
        }
        int methodLen = buf.readInt();
        if (methodLen > 0) {
            // 复用 clazz 字节数组
            byte[] methodBytes = clazzLen >= methodLen ? clazzBytes : new byte[methodLen];
            buf.readBytes(methodBytes, 0, methodLen);
            log.method = new String(methodBytes, 0, methodLen, StandardCharsets.UTF_8);
        }

        int argSize = buf.readInt();
        log.args = new String[argSize];
        if (argSize > 0) {
//            Method method = getClassMethod(log.clazz, log.method);
//            if (method == null) {
//                throw new IllegalStateException("类" + log.clazz + "方法" + log.method + "未找到");
//            }
//            Class<?>[] paramTypes = method.getParameterTypes();
//            if (paramTypes.length != argSize) {
//                throw new IllegalStateException("类" + log.clazz + "方法" + log.method + "指定参数" + argSize + "个,查到参数" + paramTypes.length + "个");
//            }
            // 复用 bytes
            byte[] argBytes = new byte[0];
            for (int i = 0; i < argSize; i++) {
                int argLen = buf.readInt();
                argBytes = argBytes.length >= argLen ? argBytes : new byte[argLen];
                buf.readBytes(argBytes, 0, argLen);
                log.args[i] = new String(argBytes, 0, argLen, StandardCharsets.UTF_8);
            }
        }
        int dataSize = buf.readInt();
        log.serializeDataCollect = dataSize > 0 ? new ArrayList<>(dataSize) : Collections.emptyList();
        if (dataSize > 0) {
            byte[] dataByte = new byte[0];
            for (int i = 0; i < dataSize; i++) {
                int dataByteLen = buf.readInt();
                dataByte = dataByte.length >= dataByteLen ? dataByte : new byte[dataByteLen];
                buf.readBytes(dataByte, 0, dataByteLen);
                log.serializeDataCollect.add(new String(dataByte, 0, dataByteLen, StandardCharsets.UTF_8));
            }
        }
        return log;
    }

    public static void main(String[] args) {
        RouterUser user1 = new RouterUser()
                .setUid(10001L)
                .setAccount("前线")
                .setImEntryAddr("127.0.0.1")
                .setIsOnline(false);
        RouterUser user2 = new RouterUser()
                .setUid(10002L)
                .setAccount("画中")
                .setImEntryAddr("127.0.0.2")
                .setIsOnline(true);
        AddLog<RouterUser> addLog = addLog(SyncTypes.ROUTER_USER)
                .addSerializeData(user1)
                .addSerializeData(user2);
//        String json = Jackson.json().serialize(addLog);
//        System.out.println(json);
//        System.out.println(json.getBytes(StandardCharsets.UTF_8).length);

        byte[] bytes = addLog.toBytes();
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        SyncLog newLog = SyncLog.read(buf);
        buf.release();
        System.out.println(bytes.length);
        System.out.println(addLog);
        System.out.println(newLog);
    }


    /**
     * TODO 缓存反射结果
     */
    @Deprecated
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

    /**
     * 新增数据
     * @param <T>
     */
    public static class AddLog<T extends DataSync> extends SyncLog {
        AddLog(SyncTypes.SyncType<T> syncType) {
            super(syncType);
            this.operateType = OPT_ADD;
        }

        public AddLog<T> setSerializeDataCollect(List<String> serializeDataCollect) {
            this.serializeDataCollect = serializeDataCollect;
            return this;
        }

        public AddLog<T> addSerializeData(T data) {
            if (this.serializeDataCollect == null) {
                this.serializeDataCollect = new ArrayList<>();
            }
            this.serializeDataCollect.add(Jackson.json().serialize(data));
            return this;
        }
    }

    /**
     * 更新数据
     * @param <T>
     */
    public static class UpdateLog<T extends DataSync> extends SyncLog {
        UpdateLog(int logSeq, SyncTypes.SyncType<T> syncType) {
            super(syncType);
            this.operateType = OPT_UPDATE;
            this.logSeq = logSeq;
        }

        public UpdateLog<T> setDataKey(String dataKey) {
            this.dataKey = dataKey;
            return this;
        }

        public UpdateLog<T> setMethod(String method) {
            this.method = method;
            return this;
        }

        public UpdateLog<T> setArgs(Object[] args) {
            this.args = new String[args.length];
            for (int i = 0; i < args.length; i++) {
                this.args[i] = Jackson.json().serialize(args[i]);
            }
            return this;
        }
    }

    /**
     * 删除数据
     * @param <T>
     */
    public static class RemoveLog<T extends DataSync> extends SyncLog {
        RemoveLog(int logSeq, SyncTypes.SyncType<T> syncType) {
            super(syncType);
            this.operateType = OPT_REMOVE;
            this.logSeq = logSeq;
        }

        public RemoveLog<T> setDataKey(String dataKey) {
            this.dataKey = dataKey;
            return this;
        }
    }

}
