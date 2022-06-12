package net.sopod.soim.client.protocol;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.client.logger.Console;
import net.sopod.soim.data.serialize.ImMessage;
import net.sopod.soim.data.serialize.ImMessageCodec;

/**
 * ImMessageInboundHandler
 *
 * @author tmy
 * @date 2022-06-02 17:51
 */
@Singleton
@ChannelHandler.Sharable
public class ImMessageInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Inject
    private ImMessageReqHolder imMessageReqHolder;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Console.info("与服务器断开连接...");
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf bytebuf) throws Exception {
        ImMessage imMessage = ImMessageCodec.decodeImMessage(bytebuf);
        // 请求序列号，complete 对应 CompletableFuture
        int serialNo = imMessage.getSerialNo();
        imMessageReqHolder.complete(serialNo, imMessage.getDecodeBody());
    }

}
