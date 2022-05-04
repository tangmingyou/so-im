package net.sopod.soim.router.config;

import net.sopod.soim.common.util.HashAlgorithms;
import net.sopod.soim.common.util.StringUtil;
import org.apache.dubbo.common.URL;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ImRouterContextHolder
 * im-router 应用上下文信息
 * TODO 启动指定 im-router 服务类型 service/backup
 * TODO backup 指定备份 im-router 或随机备份未备份 im-router 服务
 *
 * @author tmy
 * @date 2022-05-04 09:21
 */
public class ImRouterAppContextHolder {

    /**
     * 要注册的 provider 服务的 url 列表
     */
    private static final List<URL> registryInvokerUrls = new CopyOnWriteArrayList<>();

    private static String appServiceAddr;

    public static final String IM_ROUTER_ID;

    static {
        IM_ROUTER_ID = String.valueOf(HashAlgorithms.md5Hash(StringUtil.randomUUID()));
    }

    public static void addRegistryInvokerUrl(URL registryInvokerUrl) {
        registryInvokerUrls.add(registryInvokerUrl);
    }

    public static List<URL> getRegistryInvokerUrls() {
        return registryInvokerUrls;
    }

    public static void setAppServiceAddr(String appServiceAddr) {
        ImRouterAppContextHolder.appServiceAddr = appServiceAddr;
    }

    public static String getAppServiceAddr() {
        return appServiceAddr;
    }

}
