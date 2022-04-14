package net.sopod.soim.client.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.sopod.soim.core.net.ImMessageCodec;
import net.sopod.soim.data.msg.auth.Auth;
import net.sopod.soim.data.msg.hello.HelloPB;

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
                                .addLast(new ImMessageCodec());
                                // .addLast(new ProtoMessageCodec());
                    }
                });
        Channel channel = b.connect(host, port).await().channel();
        Map<String, Object> body = new HashMap<>();
        body.put("name", "二狗子");
        body.put("age", 16);
        body.put("birthday", "2002-04-19");
        HelloPB.Hello hello = HelloPB.Hello.newBuilder()
                .setId(1)
                .setStr("手")
                .build();
        Auth.ReqLogin login = Auth.ReqLogin.newBuilder()
                .setAccount("prometheus")
                .setPassword("123456")
                .build();

//        ImMessage imMessage = new ImMessage()
//                .setServiceNo(ProtoMessageManager.getSerialNo(hello.getClass()))
//                .setSerializeType(SerializeType.json.ordinal())
//                //.setBody(Jackson.json().serialize(body).getBytes(StandardCharsets.UTF_8));
//                .setBody(hello.toByteArray());
        channel.writeAndFlush(hello);
        Thread.sleep(4000);
        channel.writeAndFlush(login);
        channel.close();
        work.shutdownGracefully();
    }

}
