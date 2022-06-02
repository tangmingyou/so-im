package net.sopod.soim.client.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.data.serialize.ImMessage;

/**
 * ImMessageInboundHandler
 *
 * @author tmy
 * @date 2022-06-02 17:51
 */
public class ImMessageInboundHandler extends SimpleChannelInboundHandler<ImMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ImMessage imMessage) throws Exception {
        // 请求序列号，complete 对应 CompletableFuture
        int serialNo = imMessage.getSerialNo();

    }

}
