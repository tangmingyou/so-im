package net.sopod.soim.entry.api.route;

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
 * ImEntryServerAddressLoadBalance
 *
 * @author tmy
 * @date 2022-05-02 14:45
 */
public class ImEntryServerAddressLoadBalance extends AbstractLoadBalance {

    private static final Logger logger = LoggerFactory.getLogger(ImEntryServerAddressLoadBalance.class);

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        String invokeAddr = "";
        for (Invoker<T> invoker : invokers) {
            invokeAddr += invoker.getUrl().getAddress() + ":" + invoker.getInterface() + ",";
        }
        logger.info("invokers: {}", invokeAddr);
        String entryAddr = invocation.getAttachment(DubboConstant.IM_ENTRY_ADDR);
        if (StringUtil.isEmpty(entryAddr)) {
            throw new IllegalCallerException("上下文 im-entry 服务地址不能为空");
        }
        logger.info("entryAddr: {}", entryAddr);
        for (Invoker<T> invoker : invokers) {
            if (entryAddr.equals(invoker.getUrl().getAddress())) {
                logger.info("invoker address: {}", invoker.getUrl().getAddress());
                return invoker;
            }
        }
        throw new IllegalCallerException("没有地址为 " + entryAddr + " im-entry 服务");
    }

}
