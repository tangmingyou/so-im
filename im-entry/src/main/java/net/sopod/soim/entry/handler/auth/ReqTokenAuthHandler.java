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
 * 连接客户端携带 token 认证
 * 1.调用 token 校验解析服务获取用户信息
 *   1.1 存储用户信息、entry 节点信息 到 router
 * 2. entry channel attr(UserInfo) / return false, channel close
 *
 * return ResTokenAuth(success/false)
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
        return Auth.ResTokenAuth.newBuilder()
                .setSuccess(true)
                .setMessage("OK")
                .build();
    }

}
