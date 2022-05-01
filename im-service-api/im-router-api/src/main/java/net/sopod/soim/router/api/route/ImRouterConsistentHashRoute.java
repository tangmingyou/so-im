package net.sopod.soim.router.api.route;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.common.util.HashAlgorithms;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    /**
     * 一致性 hash 每个节点的虚拟节点数量
     */
    private static final int VIRTUAL_NODE_SIZE = 120;

    private volatile ConsistentHashSelector<?> selector;

    /**
     * 后续如有接口版本号，构建 map 每个方法版本，对应一个 Selector
     */
    @Override
    @SuppressWarnings("unchecked")
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        int invokersHash = getInvokersHash(invokers);
        logger.info("invokers hash: {}", invokersHash);
        if (selector == null || selector.identityHashCode != invokersHash) {
            selector = new ConsistentHashSelector<>(invokers, invokersHash);
        }
        return ((ConsistentHashSelector<T>)selector).select(invocation);
    }

    private <T> int getInvokersHash(List<Invoker<T>> invokers) {
        return invokers.hashCode();
    }

    static class ConsistentHashSelector<T> {

        private final TreeMap<Long, Invoker<T>> virtualInvokers;

        private final int identityHashCode;

        ConsistentHashSelector(List<Invoker<T>> invokers, int identityHashCode) {
            this.identityHashCode = identityHashCode;
            this.virtualInvokers = new TreeMap<>();

            for (Invoker<T> invoker : invokers) {
                String address = invoker.getUrl().getAddress();
                for (int i = 0, len = VIRTUAL_NODE_SIZE / 4; i < len; i++) {
                    for (int h = 0; h < 4; h++) {
                        long hash = hash(address + i, h);
                        this.virtualInvokers.put(hash, invoker);
                    }
                }
            }
        }

        private static long hash(String value, int number) {
            return HashAlgorithms.md5Hash(value, number);
        }

        public Invoker<T> select(Invocation invocation) {
            // 获取上下文 uid, 同 RpcContext
            String uid = invocation.getAttachment(DubboConstant.CTX_UID);
            if (uid == null) {
                throw new IllegalStateException("im-router consistent hash route, ctx uid can not be null!");
            }
            long hash = hash(uid, 0);
            Map.Entry<Long, Invoker<T>> entry = virtualInvokers.ceilingEntry(hash);
            if (entry == null) {
                entry = virtualInvokers.firstEntry();
            }
            return entry.getValue();
        }

    }

}
