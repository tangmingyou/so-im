package net.sopod.soim.router.config.filter;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.SoImUserCache;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * InvokeImEntryFilter
 * 调用 im-entry 服务 serverAddr 上下文设置
 * 弃用: loadbalance 先于 filter 调用
 *
 * @author tmy
 * @date 2022-05-02 14:56
 */
@Deprecated
public class InvokeImEntryFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(InvokeImEntryFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String serviceInterface = invoker.getUrl().getServiceInterface();
        logger.info("invoke interface: {}", serviceInterface);
        // 判断是 im-entry 服务接口
        if (serviceInterface.startsWith(DubboConstant.IM_ENTRY_SERVICE_API_PACK)) {
            String imEntryServerAddr = null;
            // 获取请求链路用户id
            String ctxUid = invocation.getAttachment(DubboConstant.CTX_UID);
            if (ctxUid != null) {
                SoImUserCache soImUserCache = SoImUserCache.getInstance();
                RouterUser routerUser = soImUserCache.get(Long.valueOf(ctxUid));
                if (routerUser != null) {
                    imEntryServerAddr = routerUser.getImEntryAddr();
                }
            }
            if (imEntryServerAddr == null) {
                throw new IllegalCallerException("调用im-entry服务接口,上下文服务地址未指定");
            }
            // 设置调用 im-entry 服务地址
            RpcContext.getServiceContext().setAttachment(DubboConstant.IM_ENTRY_ADDR, imEntryServerAddr);
            // invocation.setAttachment(DubboConstant.IM_ENTRY_ADDR, imEntryServerAddr);
            logger.info("invoke im-entry: uid={}, entryAddr={}", ctxUid, imEntryServerAddr);
        }
        return invoker.invoke(invocation);
    }

}
