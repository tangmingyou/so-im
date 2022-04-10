package net.sopod.soim.entry.handler;

import com.google.protobuf.MessageLite;
import net.sopod.soim.core.handler.NetUserMessageHandler;
import net.sopod.soim.core.session.NetUser;
import net.sopod.soim.data.msg.hello.HelloPB;
import org.springframework.stereotype.Service;

/**
 * HelloHandler
 *
 * @author tmy
 * @date 2022-04-10 19:19
 */
@Service
public class HelloHandler extends NetUserMessageHandler<HelloPB.Hello> {

    @Override
    public MessageLite handle(NetUser netUser, HelloPB.Hello msg) {
        System.out.println("get hello message");
        System.out.println(msg);
        System.out.println(msg.getStr());
        return null;
    }

}
