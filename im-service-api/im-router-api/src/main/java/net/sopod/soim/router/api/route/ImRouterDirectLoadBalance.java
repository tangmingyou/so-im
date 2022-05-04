package net.sopod.soim.router.api.route;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.common.util.StringUtil;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ImRouterDirectLoadBalance
 * 通过上下文指定的 router 服务地址调用 im-router 服务接口
 *
 * @author tmy
 * @date 2022-05-04 15:26
 */
public class ImRouterDirectLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "im_router_direct";

    private static final Logger logger = LoggerFactory.getLogger(ImRouterDirectLoadBalance.class);

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        String imRouterId = invocation.getAttachment(DubboConstant.IM_ROUTER_ID_KEY);
        logger.info("invocation router id: {}", imRouterId);
        if (StringUtil.isEmpty(imRouterId)) {
            throw new IllegalCallerException("上下文im-router服务id不能为空");
        }
        for (Invoker<T> invoker : invokers) {
            String invokerId = invoker.getUrl().getParameter(DubboConstant.IM_ROUTER_ID_KEY);
            logger.info("invoker router id: {}", invokerId);
            if (imRouterId.equals(invokerId)) {
                return invoker;
            }
        }
        throw new IllegalCallerException("没有id为" + imRouterId + "的im-router服务");
    }

}
