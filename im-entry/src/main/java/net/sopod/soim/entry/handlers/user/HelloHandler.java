package net.sopod.soim.entry.handlers.user;

import com.google.protobuf.MessageLite;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.data.msg.hello.HelloPB;
import net.sopod.soim.entry.delay.NetUserDelayTaskManager;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import net.sopod.soim.logic.api.user.service.UserBizService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * HelloHandler
 *
 * @author tmy
 * @date 2022-04-10 19:19
 */
@Service
public class HelloHandler extends AccountMessageHandler<HelloPB.Hello> {

    private static final Logger logger = LoggerFactory.getLogger(HelloHandler.class);

    @DubboReference(methods = {@Method(name = "sayHello", async = true)})
    private UserBizService userBizService;

    @Autowired
    private SegmentIdGenerator segmentIdGenerator;

    @Override
    public MessageLite handle(ImContext ctx, Account account, HelloPB.Hello msg) {
        logger.info("get hello message: {}", segmentIdGenerator.nextId("im-entry-hello"));
        logger.info("msg={}, {}", msg.getId(), msg.getStr());
        CompletableFuture<String> hi = userBizService.sayHi("黄绿");
        hi.whenComplete((res, err) -> {
           logger.info("async hi, {}", res);
        });
        String hello = userBizService.sayHello("lastJet");
        logger.info("hello:{}", hello);
        RpcContext.getServiceContext().getCompletableFuture().whenComplete((res, err) -> {
            logger.info("async sayHello, {}", res);
        });
        NetUserDelayTaskManager.addTask(account, msg, 5, TimeUnit.SECONDS);
        return null;
    }

}
