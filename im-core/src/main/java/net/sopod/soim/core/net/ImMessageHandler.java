package net.sopod.soim.core.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.data.constant.SerializeType;
import net.sopod.soim.data.serialize.ImMessage;

import java.util.Map;

/**
 * ImMessageHandler
 *
 * @author tmy
 * @date 2022-03-28 13:27
 */
public class ImMessageHandler extends SimpleChannelInboundHandler<ImMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ImMessage imMessage) throws Exception {
        byte[] body = imMessage.getBody();
        SerializeType serialize = SerializeType.getSerializeByOrdinal(imMessage.getSerializeType());
        Map data = serialize.getSerializer().deserialize(body, Map.class);
        System.out.println(data);
    }

}
