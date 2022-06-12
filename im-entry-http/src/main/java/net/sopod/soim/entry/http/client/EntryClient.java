package net.sopod.soim.entry.http.client;

import com.google.protobuf.MessageLite;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.common.dubbo.exception.SoimException;
import net.sopod.soim.common.util.netty.Varint32FrameCodec;
import net.sopod.soim.data.msg.monitor.EntryMonitor;
import net.sopod.soim.data.serialize.ImMessage;
import net.sopod.soim.data.serialize.ImMessageCodec;
import net.sopod.soim.entry.http.service.ImMessageReqHolder;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * EntryClient
 *
 * @author tmy
 * @date 2022-06-11 13:17
 */
public class EntryClient {

    private static final Logger logger = LoggerFactory.getLogger(EntryClient.class);

    public static AttributeKey<EntryClient> IM_ENTRY_CLIENT_KEY = AttributeKey.valueOf(EntryClient.class, "IM_ENTRY_CLIENT_KEY");

    private final String entryAddress;

    private final ImMessageReqHolder imMessageReqHolder;

    private Channel clientChannel;

    private NioEventLoopGroup eventLoopGroup;

    private int entryConnections;

    private long updateTime;

    public EntryClient(String entryAddress) {
        this.entryAddress = entryAddress;
        this.imMessageReqHolder = new ImMessageReqHolder();
    }

    public boolean connect() {
        this.eventLoopGroup = new NioEventLoopGroup(2);
        Bootstrap b = new Bootstrap()
                .group(this.eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new Varint32FrameCodec())
                                .addLast(new ImMessageCodec.ImMessage2ByteEncoder())
                                .addLast(new ImMessageInboundHandler());
                    }
                })
                .option(ChannelOption.TCP_NODELAY, true);
        String[] hostPort = this.entryAddress.split(":");
        try {
            this.clientChannel = b.connect(hostPort[0], Integer.parseInt(hostPort[1])).await().channel();
        } catch (InterruptedException e) {
            logger.error("im-entry 连接失败: ", e);
            return false;
        }
        // 设置 MessageHolder 到 channel
        this.clientChannel.attr(ImMessageReqHolder.IM_MESSAGE_HOLDER_KEY).set(this.imMessageReqHolder);
        // 设置 client 到 channel
        this.clientChannel.attr(EntryClient.IM_ENTRY_CLIENT_KEY).set(this);
        EntryMonitor.ReqMonitorAuth req = EntryMonitor.ReqMonitorAuth.newBuilder()
                .setSecurity(AppConstant.IM_ENTRY_MONITOR_SECURITY)
                .build();
        CompletableFuture<?> future = this.send0(req);
        EntryMonitor.ResMonitorAuth res = (EntryMonitor.ResMonitorAuth) future.join();
        if (!res.getSuccess()) {
            logger.error("{} 认证失败", this.entryAddress);
            // 关闭连接
            this.close();
            return false;
        }
        logger.info("认证成功: {}", this.entryAddress);
        return true;
    }

    private CompletableFuture<?> send0(MessageLite message) {
        if (this.clientChannel == null
                || !this.clientChannel.isActive()) {
            // TODO 尝试重连
            throw new SoimException("连接已关闭");
        }
        // 构建 ImMessage
        ImMessage imMessage = ImMessageCodec.encodeImProto(message);
        // serialNo, Future
        Pair<Integer, CompletableFuture<Object>> futurePair = imMessageReqHolder.nextSerialNo();
        imMessage.setSerialNo(futurePair.getLeft());
        try {
            Throwable cause = this.clientChannel.writeAndFlush(imMessage).await().cause();
            if (cause != null) {
                throw new SoimException(cause);
            }
        } catch (InterruptedException e) {
            throw new SoimException("发送消息失败", e);
        }
        return futurePair.getRight();
    }

    /**
     * 发送消息
     */
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> send(MessageLite message) {
        return (CompletableFuture<T>) send0(message);
    }

    public String getEntryAddress() {
        return entryAddress;
    }

    public void updateEntryStatus(int connections, long time) {
        // logger.info("{} entry status: conns={}", this.entryAddress, connections);
        this.entryConnections = connections;
        this.updateTime = time;
    }

    /**
     * 关闭 monitor client 连接
     */
    public void close() {
        if (this.clientChannel != null) {
            this.clientChannel.close();
            this.clientChannel.attr(ImMessageReqHolder.IM_MESSAGE_HOLDER_KEY).set(null);
            this.clientChannel.attr(EntryClient.IM_ENTRY_CLIENT_KEY).set(null);
            logger.info("entry monitor client {} closed.", this.entryAddress);
        }
        if (this.eventLoopGroup != null) {
            this.eventLoopGroup.shutdownGracefully();
        }
    }

    public int getEntryConnections() {
        return entryConnections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntryClient that = (EntryClient) o;
        return Objects.equals(entryAddress, that.entryAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryAddress);
    }

    @Override
    public String toString() {
        return "EntryClient{" +
                "entryAddress='" + entryAddress + '\'' +
                ", entryConnections=" + entryConnections +
                ", updateTime=" + updateTime +
                '}';
    }
}
