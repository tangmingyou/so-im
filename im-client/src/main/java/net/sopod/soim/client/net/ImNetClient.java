package net.sopod.soim.client.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.core.net.ImEntryCodec;
import net.sopod.soim.data.constant.SerializeType;
import net.sopod.soim.data.serialize.ImMessage;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * ClientInitial
 *
 * @author tmy
 * @date 2022-03-27 22:36
 */
public class ImNetClient {

    public void connect(String host, int port) throws InterruptedException {
        NioEventLoopGroup work = new NioEventLoopGroup(2);
        Bootstrap b = new Bootstrap()
                .group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new ImEntryCodec());
                    }
                });
        Channel channel = b.connect(host, port).await().channel();
        Map<String, Object> body = new HashMap<>();
        body.put("name", "二狗子");
        body.put("age", 16);
        ImMessage imMessage = new ImMessage()
                .setServiceNo(1)
                .setSerializeType(SerializeType.json.ordinal())
                .setBody(Jackson.json().serialize(body).getBytes(StandardCharsets.UTF_8));
        channel.writeAndFlush(imMessage);
        channel.close();
        work.shutdownGracefully();
    }


}
