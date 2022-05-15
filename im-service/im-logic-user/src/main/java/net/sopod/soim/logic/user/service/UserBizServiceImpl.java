package net.sopod.soim.logic.user.service;

import net.sopod.soim.logic.api.user.service.UserBizService;
import net.sopod.soim.logic.common.model.UserInfo;
import net.sopod.soim.router.api.service.UserRouteService;
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
public class UserBizServiceImpl implements UserBizService {

    private static final Logger logger = LoggerFactory.getLogger(UserBizServiceImpl.class);

    @DubboReference
    private UserRouteService userRouteService;

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
        logger.info("service context uid: {}", RpcContext.getServiceContext().getAttachment("uid"));
        return userRouteService.onlineUserList(keyword);
    }

}
