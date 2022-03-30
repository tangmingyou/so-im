package net.sopod.soim.entry.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

/**
 * HelloServiceImpl
 *
 * @author tmy
 * @date 2022-03-28 16:23
 */
@DubboService(version = "1.0.0")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("be invoked! client:" +  RpcContext.getClientAttachment().getObjectAttachments());
        return "hello "+ name;
    }

}
