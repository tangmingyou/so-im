package net.sopod.soim.das.user.service;

import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import javax.annotation.Resource;

/**
 * HelloServiceImpl
 *
 * @author tmy
 * @date 2022-03-28 16:23
 */
@DubboService(version = "1.0.0")
public class HelloServiceImpl implements HelloService {

    @Resource
    private SegmentIdGenerator segmentIdGenerator;

    @Override
    public String sayHello(String name) {
        System.out.println("be invoked! client:" +  RpcContext.getClientAttachment().getObjectAttachments());
        return "hello "+ name;
    }

}
