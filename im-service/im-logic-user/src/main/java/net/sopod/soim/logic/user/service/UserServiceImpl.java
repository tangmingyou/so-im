package net.sopod.soim.logic.user.service;

import net.sopod.soim.logic.common.model.UserInfo;
import net.sopod.soim.router.api.service.UserEntryRegistryService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * UserServiceImpl
 *
 * @author tmy
 * @date 2022-04-04 10:03
 */
@DubboService
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @DubboReference
    private UserEntryRegistryService userEntryRegistryService;

    @Override
    public CompletableFuture<String> sayHi(String name) {
        return CompletableFuture.completedFuture("hi," + name + "!");
    }

    @Override
    public String sayHello(String name) {
        return "hello," + name + "!";
    }

    @Override
    public List<UserInfo> onlineUserList(String keyword) {
        logger.info("client context uid: {}", RpcContext.getClientAttachment().getAttachment("uid"));
        logger.info("server context uid: {}", RpcContext.getServerAttachment().getAttachment("uid"));
        return userEntryRegistryService.onlineUserList(keyword);
    }

}
