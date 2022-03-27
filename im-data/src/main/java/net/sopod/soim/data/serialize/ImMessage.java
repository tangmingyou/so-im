package net.sopod.soim.data.serialize;

import io.netty.buffer.ByteBuf;

import java.util.Random;

/**
 * Struct
 * 是否是事件，是否需要返回值
 *
 * @author tmy
 * @date 2022-03-27 17:13
 */
public class ImMessage {

    public static final short MAGIC = 0x7a20;

    public static final int MESSAGE_HEAD_LEN = 17;

    public static final ImMessage PROTOCOL_ERROR;

    public static final ImMessage MAGIC_ERROR;

    static {
        Random random = new Random();
        PROTOCOL_ERROR = new ImMessage().setSerialNo(random.nextInt());
        MAGIC_ERROR = new ImMessage().setSerialNo(random.nextInt());
    }

    /** 协议版本 */
    //private int version;

    /** 协议总长度 */
    //private int protocolLength;

    /** 请求序列号 */
    private int serialNo;

    /** 服务编号 */
    private int serviceNo;

    /** 请求体序列化格式 Ordinal */
    private int serializeType;

    /** 压缩算法 */
    private int zipType;

    /** 平台号 */
    private int platformNo;

    /** 请求体长度 */
    private int bodyLength;

    private byte[] body;

    /**
     * 从 bytebuf 流读取
     */
    public static ImMessage read(ByteBuf buf) {
        int bufLen = buf.readableBytes();
        if (bufLen < MESSAGE_HEAD_LEN) {
            return PROTOCOL_ERROR;
        }
        short magic = buf.readShort();
        if (magic != MAGIC) {
            return MAGIC_ERROR;
        }
        ImMessage msg = new ImMessage()
                .setSerialNo(buf.readInt())
                .setServiceNo(buf.readInt())
                .setSerializeType(buf.readByte())
                .setZipType(buf.readByte())
                .setPlatformNo(buf.readByte());
        msg.bodyLength = buf.readInt();
        if (bufLen != MESSAGE_HEAD_LEN + msg.getBodyLength()) {
            return PROTOCOL_ERROR;
        }
        byte[] body = new byte[msg.getBodyLength()];
        buf.readBytes(body);
        msg.setBody(body);
        return msg;
    }

    /**
     * 写入到 bytebuf 流
     */
    public void write(ByteBuf buf) {
        buf.writeShort(MAGIC)
            .writeInt(this.serialNo)
            .writeInt(this.serviceNo)
            .writeByte(this.serializeType)
            .writeByte(this.zipType)
            .writeByte(this.platformNo);
        if (this.body == null) {
            buf.writeInt(0);
        } else {
            buf.writeInt(this.body.length)
                .writeBytes(this.body);
        }
    }

    public int getSerialNo() {
        return serialNo;
    }

    public ImMessage setSerialNo(int serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public int getServiceNo() {
        return serviceNo;
    }

    public ImMessage setServiceNo(int serviceNo) {
        this.serviceNo = serviceNo;
        return this;
    }

    public int getSerializeType() {
        return serializeType;
    }

    public ImMessage setSerializeType(int serializeType) {
        this.serializeType = serializeType;
        return this;
    }

    public int getZipType() {
        return zipType;
    }

    public ImMessage setZipType(int zipType) {
        this.zipType = zipType;
        return this;
    }

    public int getPlatformNo() {
        return platformNo;
    }

    public ImMessage setPlatformNo(int platformNo) {
        this.platformNo = platformNo;
        return this;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public byte[] getBody() {
        return body;
    }

    public ImMessage setBody(byte[] body) {
        this.body = body;
        return this;
    }
}
