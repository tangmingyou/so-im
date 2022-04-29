package net.sopod.soim.router.api.route;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;

import java.util.List;

/**
 * ConsistentHashRoute
 * 一致性 hash 服务路由
 *
 *
 * @author tmy
 * @date 2022-04-29 14:41
 */
public class ConsistentHashRoute extends AbstractLoadBalance {

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        Invoker<T> invoker = invokers.get(0);
        return null;
    }

}
