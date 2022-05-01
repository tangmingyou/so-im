package net.sopod.soim.entry.handler.auth;

import com.google.protobuf.MessageLite;
import net.sopod.soim.entry.handler.NetUserMessageHandler;
import net.sopod.soim.core.session.Account;
import net.sopod.soim.core.session.NetUser;
import net.sopod.soim.data.msg.auth.Auth;
import net.sopod.soim.entry.config.EntryServerConfig;
import net.sopod.soim.entry.server.AccountRegistry;
import net.sopod.soim.logic.user.auth.service.UserAuthService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @DubboReference
    private UserAuthService userAuthService;

    @Resource
    private EntryServerConfig entryServerConfig;

    @Resource
    private AccountRegistry accountRegistry;

    @Override
    public MessageLite handle(NetUser netUser, Auth.ReqTokenAuth msg) {
        logger.info("ReqTokenAuth: uid={}", msg.getUid());
        // 校验 token，
        // TODO 更新 router 状态成功后再升级为 account，主动关闭时发送一个消息到客户端，校验错误/超时
        Boolean isValid = userAuthService.validateToken(msg.getToken(),
                entryServerConfig.getIp() + ":" + entryServerConfig.getPort());

        if (!Boolean.TRUE.equals(isValid)) {
            return Auth.ResTokenAuth.newBuilder()
                    .setSuccess(false)
                    .setMessage("token校验失败,请重新登录")
                    .build();
        }
        // 将连接升级为账户
        Account account = Account.AccountBuilder.newBuilder()
                .setNetUser(netUser)
                .setUid(msg.getUid())
                .setName("") // TODO 账户名处理
                .build();
        netUser.upgradeAccount(account);

        // 记录登录账户
        accountRegistry.put(account);

        return Auth.ResTokenAuth.newBuilder()
                .setSuccess(true)
                .setMessage("OK")
                .setUid(msg.getUid())
                .build();
    }

}
