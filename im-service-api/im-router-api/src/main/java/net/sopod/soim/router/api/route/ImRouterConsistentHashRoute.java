package net.sopod.soim.router.api.route;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.common.util.Collects;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ConsistentHashRoute
 * 一致性 hash 服务路由
 *
 * {@link org.apache.dubbo.rpc.cluster.loadbalance.ConsistentHashLoadBalance}
 *
 * @author tmy
 * @date 2022-04-29 14:41
 */
public class ImRouterConsistentHashRoute extends AbstractLoadBalance {

    private static final Logger logger = LoggerFactory.getLogger(ImRouterConsistentHashRoute.class);

    private volatile UidConsistentHashSelector<?> selector;

    /**
     * 后续如有接口版本号，构建 map 每个方法版本，对应一个 Selector
     */
    @Override
    @SuppressWarnings("unchecked")
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        int invokersHash = getInvokersHash(invokers);
        // logger.info("invokers hash: {}", invokersHash);

        if (selector == null || selector.getIdentityHashCode() != invokersHash) {
            // 调用节点有变化，构建新的 hash 表
            Map<String, Invoker<T>> serverAddrInvokerMap = Collects.collect2Map(invokers,
                    invoker -> invoker.getUrl().getAddress(),
                    new HashMap<>(6));
            selector = new UidConsistentHashSelector<>(serverAddrInvokerMap, invokersHash);
            selector.asCurrent();
        }
        // 获取上下文 uid, 同 RpcContext
        String uid = invocation.getAttachment(DubboConstant.CTX_UID);
        return (Invoker<T>) selector.select(uid);
    }

    private <T> int getInvokersHash(List<Invoker<T>> invokers) {
        return invokers.hashCode();
    }

}
