package net.sopod.soim.entry.config;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.ExporterListener;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * DubboExporterListener
 * 获取服务注册地址 im-entry
 *
 * @author tmy
 * @date 2022-05-02 09:55
 */
public class DubboImEntryExporterListener implements ExporterListener {

    private static final Logger logger = LoggerFactory.getLogger(DubboImEntryExporterListener.class);

    @Override
    public void exported(Exporter<?> exporter) throws RpcException {
        URL invokerUrl = exporter.getInvoker().getUrl();
        if (DubboProtocol.NAME.equals(invokerUrl.getProtocol())) {
            if (ApplicationContextHolder.getDubboAppServiceAddr() == null) {
                logger.info("im-entry dubbo app serverAddr: {}", invokerUrl.getAddress());
                ApplicationContextHolder.setDubboAppServiceAddr(invokerUrl.getAddress());
            }
        }
    }

    @Override
    public void unexported(Exporter<?> exporter) {

    }

}
