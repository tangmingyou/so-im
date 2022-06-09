package net.sopod.soim.entry.config;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.ExporterListener;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.protocol.injvm.InjvmProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DubboExporterListener
 * 获取服务注册地址 im-entry
 *
 * @author tmy
 * @date 2022-05-02 09:55
 */
public class ImEntryAPIExporterListener implements ExporterListener {

    private static final Logger logger = LoggerFactory.getLogger(ImEntryAPIExporterListener.class);

    @Override
    public void exported(Exporter<?> exporter) throws RpcException {
        URL invokerUrl = exporter.getInvoker().getUrl();
        if (!InjvmProtocol.NAME.equals(invokerUrl.getProtocol())) {
            if (ImEntryAppContext.getEntryAppAddr() == null) {
                ImEntryAppContext.setAppServiceAddr(invokerUrl.getHost(), invokerUrl.getPort());
                logger.info("im-entry dubbo service addr: {}", invokerUrl.getAddress());
            }
        }
    }

    @Override
    public void unexported(Exporter<?> exporter) {
        logger.info("unexported listener: {}", exporter.getInvoker().getInterface());
    }

}
