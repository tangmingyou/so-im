package net.sopod.soim.entry.handler;

import com.google.protobuf.MessageLite;
import net.sopod.soim.core.handler.NetUserMessageHandler;
import net.sopod.soim.core.session.NetUser;
import net.sopod.soim.data.msg.hello.HelloPB;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import net.sopod.soim.logic.user.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * HelloHandler
 *
 * @author tmy
 * @date 2022-04-10 19:19
 */
@Service
public class HelloHandler extends NetUserMessageHandler<HelloPB.Hello> {

    private static final Logger logger = LoggerFactory.getLogger(HelloHandler.class);

    @DubboReference(methods = {@Method(name = "sayHello", async = true)})
    private UserService userService;

    @Autowired
    private SegmentIdGenerator segmentIdGenerator;

    @Override
    public MessageLite handle(NetUser netUser, HelloPB.Hello msg) {
        logger.info("get hello message: {}", segmentIdGenerator.nextId("im-entry-hello"));
        logger.info("msg={}, {}", msg.getId(), msg.getStr());
        CompletableFuture<String> hi = userService.sayHi("黄绿");
        hi.whenComplete((res, err) -> {
           logger.info("async hi, {}", res);
        });
        String hello = userService.sayHello("lastJet");
        logger.info("hello:{}", hello);
        RpcContext.getServiceContext().getCompletableFuture().whenComplete((res, err) -> {
            logger.info("async sayHello, {}", res);
        });
        return null;
    }

}
