package net.sopod.soim.entry.handler.auth;

import com.google.protobuf.MessageLite;
import net.sopod.soim.core.handler.NetUserMessageHandler;
import net.sopod.soim.core.session.Account;
import net.sopod.soim.core.session.NetUser;
import net.sopod.soim.data.msg.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * LoginHandler
 *
 * @author tmy
 * @date 2022-04-13 22:20
 */
@Service
public class LoginHandler extends NetUserMessageHandler<Auth.ReqLogin> {

    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public MessageLite handle(NetUser netUser, Auth.ReqLogin msg) {
        logger.info("auth login: {}, {}", msg.getAccount(), msg.getPassword());
        Account account = Account.AccountBuilder.newBuilder()
                .setNetUser(netUser)
                .setAccountId(1L)
                .setName(msg.getAccount())
                .build();
        // 注册为账号添加到连接
        netUser.upgradeAccount(account);
        return null;
    }

}
