package net.sopod.soim.entry.config;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.logic.common.util.RpcContextUtil;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DubboPreInvokeFilter
 * 每次调用服务前设置上下文属性
 *
 * @author tmy
 * @date 2022-05-01 08:55
 */
public class DubboPreInvokeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DubboPreInvokeFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String uid = MessageHandlerContext.getAttribute(DubboConstant.CTX_UID);
        if (uid != null) {
            // 设置调用上下文 uid
            RpcContextUtil.setContextUid(uid);
            if (logger.isDebugEnabled()) {
                logger.debug("pre invoke filter set uid: {}", uid);
            }
        }
        return invoker.invoke(invocation);
    }

}
