package net.sopod.soim.entry.handler.auth;

import com.google.protobuf.MessageLite;
import net.sopod.soim.core.handler.NetUserMessageHandler;
import net.sopod.soim.core.session.NetUser;
import net.sopod.soim.data.msg.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * ReqTokenAuthHandler
 *
 * @author tmy
 * @date 2022-04-26 17:54
 */
@Service
public class ReqTokenAuthHandler extends NetUserMessageHandler<Auth.ReqTokenAuth> {

    private static final Logger logger = LoggerFactory.getLogger(ReqTokenAuthHandler.class);

    @Override
    public MessageLite handle(NetUser netUser, Auth.ReqTokenAuth msg) {
        logger.info("ReqTokenAuth: {}, {}", msg.getUid(), msg.getToken());
        return null;
    }

}
