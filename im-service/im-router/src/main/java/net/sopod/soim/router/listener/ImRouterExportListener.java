package net.sopod.soim.router.listener;

import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.ExporterListener;
import org.apache.dubbo.rpc.RpcException;

/**
 * ImRouterExportListener
 *
 * @author tmy
 * @date 2022-05-01 16:31
 */
public class ImRouterExportListener implements ExporterListener {

    @Override
    public void exported(Exporter<?> exporter) throws RpcException {

    }

    @Override
    public void unexported(Exporter<?> exporter) {

    }

}
