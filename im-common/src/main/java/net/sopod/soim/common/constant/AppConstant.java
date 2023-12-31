package net.sopod.soim.common.constant;

/**
 * AppConstant
 *
 * @author tmy
 * @date 2022-04-27 15:04
 */
public interface AppConstant {

    String APP_REGISTRY_GROUP = "so-im";

    /** im-entry 注册中心名称 */
    String APP_IM_ENTRY_REGISTRY_NAME = "im-entry-instance";

    String APP_IM_ENTRY_NAME = "im-entry";

    String APP_IM_ROUTER_NAME = "im-router";

    String APP_IM_HTTP_ENTRY_NAME = "im-http-entry";
    String APP_IM_DAS_USER_NAME = "im-das-user";

    /**
     * im-entry 端口偏移量
     */
    int IM_ENTRY_SERVER_OFFSET = 1000;

    /**
     * im-router 端口偏移量
     */
    int IM_ROUTER_SYNC_SERVER_OFFSET = 1000;

    String IM_ENTRY_MONITOR_SECURITY = "b699d74ae95011ec8373bc97e1e99320";

}
