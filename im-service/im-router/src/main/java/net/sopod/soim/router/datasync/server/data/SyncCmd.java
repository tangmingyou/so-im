package net.sopod.soim.router.datasync.server.data;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.dubbo.common.io.Bytes;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SyncCmd
 *
 * @author tmy
 * @date 2022-05-07 21:41
 */
@Data
@Accessors(chain = true)
public class SyncCmd {

    private static final short MAGIC = 0x7a21;

    public static final int PING = 1;

    public static final int PONG = 2;

    /**
     * 全量同步命令
     */
    public static final Integer SYNC_FULL = 3;

    /**
     * 计算一致性hash同步数据
     */
    public static final int SYNC_BY_HASH = 4;

    /**
     * 一致性hash同步数据，收到后ACK响应
     */
    public static final int SYNC_BY_HASH_ACK = 5;

    /**
     * 同步结束命令
     */
    public static final Integer SYNC_END = 8;

    /**
     * SyncLog 推送命令
     */
    public static final Integer SYNC_LOG = 10;

    public static SyncCmd syncByHash(String hashNode) {
        SyncCmd syncCmd = new SyncCmd();
        syncCmd.setCmdType(SYNC_BY_HASH);
        syncCmd.setParam1(hashNode);
        return syncCmd;
    }

    private Integer cmdType;

    private String param1;

    private String param2;

    private String param3;

    public byte[] toByte() {
        byte[] param1Bytes = param1 == null ? new byte[0] : param1.getBytes(StandardCharsets.UTF_8);
        byte[] param2Bytes = param2 == null ? new byte[0] : param2.getBytes(StandardCharsets.UTF_8);
        byte[] param3Bytes = param3 == null ? new byte[0] : param3.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = new byte[2 + 4 + 4 + param1Bytes.length + 4 + param2Bytes.length + 4 + param3Bytes.length];

        int offset = 0;
        Bytes.short2bytes(MAGIC, bytes, offset);
        offset += 2;
        Bytes.int2bytes(cmdType, bytes, offset);
        offset += 4;
        Bytes.int2bytes(param1Bytes.length, bytes, offset);
        offset += 4;
        System.arraycopy(param1Bytes, 0, bytes, offset, param1Bytes.length);
        offset += param1Bytes.length;

        Bytes.int2bytes(param2Bytes.length, bytes, offset);
        offset += 4;
        System.arraycopy(param2Bytes, 0, bytes, offset, param2Bytes.length);
        offset += param2Bytes.length;

        Bytes.int2bytes(param3Bytes.length, bytes, offset);
        offset += 4;
        System.arraycopy(param3Bytes, 0, bytes, offset, param3Bytes.length);
        offset += param3Bytes.length;
        return bytes;
    }

    public static SyncCmd read(ByteBuf buf) {
        short magic = buf.readShort();
        if (magic != MAGIC) {
            throw new IllegalStateException("unknown bytes magic error");
        }
        SyncCmd cmd = new SyncCmd();
        cmd.cmdType = buf.readInt();
        int param1Len = buf.readInt();
        byte[] paramBytes = new byte[param1Len];
        if (param1Len > 0) {
            buf.readBytes(paramBytes);
            cmd.param1 = new String(paramBytes, StandardCharsets.UTF_8);
        }
        int param2Len = buf.readInt();
        if (param2Len > 0) {
            paramBytes = paramBytes.length >= param2Len ? paramBytes : new byte[param2Len];
            buf.readBytes(paramBytes, 0, param2Len);
            cmd.param2 = new String(paramBytes, 0, param2Len, StandardCharsets.UTF_8);
        }
        int param3Len = buf.readInt();
        if (param3Len > 0) {
            paramBytes = paramBytes.length >= param3Len ? paramBytes : new byte[param3Len];
            buf.readBytes(paramBytes, 0, param3Len);
            cmd.param3 = new String(paramBytes, 0, param3Len, StandardCharsets.UTF_8);
        }
        return cmd;
    }

    public static void main(String[] args) {
        AtomicBoolean locked = new AtomicBoolean(false);
        System.out.println(locked.compareAndSet(false, false));
        System.out.println(locked.compareAndExchange(false, true));

    }

}
