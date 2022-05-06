package net.sopod.soim.router.config.loadbalance;

import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.logic.common.util.RpcContextUtil;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ImEntryServerAddressLoadBalance
 *
 * @author tmy
 * @date 2022-05-02 14:45
 */
public class ImEntryServerAddressLoadBalance extends AbstractLoadBalance {

    private static final Logger logger = LoggerFactory.getLogger(ImEntryServerAddressLoadBalance.class);

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        // 调用 im-entry 服务的地址
        String entryAddr = null;
        // 获取请求链路用户id
        // String ctxUid = invocation.getAttachment(DubboConstant.CTX_UID);
        String ctxUid = RpcContextUtil.getContextUid();
        if (StringUtil.isEmpty(ctxUid)) {
            throw new IllegalCallerException("调用im-entry服务接口,上下文uid未指定");
        }
        RouterUserStorage routerUserStorage = RouterUserStorage.getInstance();
        RouterUser routerUser = routerUserStorage.get(Long.valueOf(ctxUid));
        if (routerUser != null) {
            entryAddr = routerUser.getImEntryAddr();
        }
        if (entryAddr == null) {
            throw new IllegalCallerException("调用im-entry服务接口,上下文entry服务地址未指定");
        }
        logger.info("invoke im-entry: uid={}, im-entry addr={}", ctxUid, entryAddr);
        // 返回地址为 entryAddr 的 invoker
        for (Invoker<T> invoker : invokers) {
            if (entryAddr.equals(invoker.getUrl().getAddress())) {
                return invoker;
            }
        }
        // TODO 自定义异常，用户重新登录
        throw new IllegalCallerException("没有地址为 " + entryAddr + " im-entry 服务");
    }

}
