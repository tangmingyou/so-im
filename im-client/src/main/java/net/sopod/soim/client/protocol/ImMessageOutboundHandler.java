package net.sopod.soim.client.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import net.sopod.soim.data.serialize.ImMessage;

/**
 * ImMessageOutboundHandler
 *
 * @author tmy
 * @date 2022-06-02 17:53
 */
public class ImMessageOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof ImMessage)) {
            return;
        }
        ImMessage imMessage = (ImMessage) msg;
        imMessage.setSerialNo(10086);
        // queue.add something..., 调用的地方，通过阻塞方式 FastThreadLocal 获取一个 CompletableFuture

        super.write(ctx, msg, promise);
    }

}
