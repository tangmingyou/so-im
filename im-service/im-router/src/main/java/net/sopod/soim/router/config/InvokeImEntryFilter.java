package net.sopod.soim.router.config;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.router.api.model.RouterUser;
import net.sopod.soim.router.cache.SoImUserCache;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * InvokeImEntryFilter
 * 调用 im-entry 服务 serverAddr 上下文设置
 *
 * @author tmy
 * @date 2022-05-02 14:56
 */
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
            String uid2 = RpcContext.getServerContext().getAttachment(DubboConstant.CTX_UID);
            String uid3 = RpcContext.getServiceContext().getAttachment(DubboConstant.CTX_UID);
            logger.info("ctxUid: {}, {}, {}", ctxUid, uid2, uid3);
            // 设置 im-entry 服务地址
            invocation.setAttachment(DubboConstant.IM_ENTRY_ADDR, imEntryServerAddr);
        }
        return invoker.invoke(invocation);
    }

}
