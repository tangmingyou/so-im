package net.sopod.soim.entry.http.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.data.serialize.ImMessage;
import net.sopod.soim.data.serialize.ImMessageCodec;
import net.sopod.soim.entry.http.service.ImMessageReqHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ImMessageInboundHandler
 *
 * @author tmy
 * @date 2022-06-11 15:12
 */
public class ImMessageInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger logger = LoggerFactory.getLogger(ImMessageInboundHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channel active...");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        EntryClient entryClient = ctx.channel().attr(EntryClient.IM_ENTRY_CLIENT_KEY).get();
        if (entryClient != null) {
            entryClient.close();
        }
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        ImMessage imMessage = ImMessageCodec.decodeImMessage(byteBuf);
        // 请求序列号，complete 对应 CompletableFuture
        int serialNo = imMessage.getSerialNo();

        ImMessageReqHolder imMessageReqHolder = ctx.channel().attr(ImMessageReqHolder.IM_MESSAGE_HOLDER_KEY).get();
        imMessageReqHolder.complete(serialNo, imMessage.getDecodeBody());
    }

}
