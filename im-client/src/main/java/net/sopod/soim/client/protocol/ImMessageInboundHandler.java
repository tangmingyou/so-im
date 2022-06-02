package net.sopod.soim.client.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.data.serialize.ImMessage;
import net.sopod.soim.data.serialize.ImMessageCodec;

/**
 * ImMessageInboundHandler
 *
 * @author tmy
 * @date 2022-06-02 17:51
 */
public class ImMessageInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf bytebuf) throws Exception {
        ImMessage imMessage = ImMessageCodec.decodeImMessage(bytebuf);
        // 请求序列号，complete 对应 CompletableFuture
        int serialNo = imMessage.getSerialNo();
        MessageQueueHolder.getInstance().futureComplete(serialNo, imMessage.getDecodeBody());
    }

}
