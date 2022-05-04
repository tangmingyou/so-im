package net.sopod.soim.common.constant;

/**
 * DubboConstant
 *
 * @author tmy
 * @date 2022-05-01 15:26
 */
public interface DubboConstant {

    /**
     * 服务调用上下文用户id
     */
    String CTX_UID = "uid";

    /**
     * 请求 im-entry 服务地址
     */
    String IM_ENTRY_ADDR = "entry_addr";
    /**
     * 请求 im-router 服务id
     */
    String IM_ROUTER_ID_KEY = "im_router_id";

    /**
     * im-entry 服务接口前缀
     */
    String IM_ENTRY_SERVICE_API_PACK = "net.sopod.soim.entry.api.service";

}
