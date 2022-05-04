package net.sopod.soim.router.config.listener;

import net.sopod.soim.router.config.ImRouterAppContextHolder;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.ExporterListener;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.protocol.injvm.InjvmProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ImRouterExportListener
 * 监听需要注册的服务URL，存储到context，后根据需要注册到注册中心
 *
 * @author tmy
 * @date 2022-05-01 16:31
 */
public class ImRouterAPIExportListener implements ExporterListener {

    private static final Logger logger = LoggerFactory.getLogger(ImRouterAPIExportListener.class);

    @Override
    public void exported(Exporter<?> exporter) throws RpcException {
        URL invokerUrl = exporter.getInvoker().getUrl();
        if (!InjvmProtocol.NAME.equals(invokerUrl.getProtocol())) {
            ImRouterAppContextHolder.addRegistryInvokerUrl(invokerUrl);
            if (ImRouterAppContextHolder.getAppServiceAddr() == null) {
                ImRouterAppContextHolder.setAppServiceAddr(invokerUrl.getAddress());
                logger.info("im-router registry serverAddr: {}", invokerUrl.getAddress());
            }
        }
    }

    @Override
    public void unexported(Exporter<?> exporter) {
        logger.info("unexported listener: {}", exporter.getInvoker().getInterface());
    }

}
