package net.sopod.soim.router.util;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Objects;

/**
 * RpcContextUtil
 *
 * @author tmy
 * @date 2022-05-02 22:27
 */
@Deprecated
public class RpcContextUtil {

    public static boolean setImEntryRouteServerAddrByUid(Long uid) {
        Objects.requireNonNull(uid, "设置im-entry路由参数uid不能为空");
        RpcContext.getServiceContext().setAttachment(DubboConstant.CTX_UID, uid);
        RouterUser routerUser = RouterUserStorage.getInstance().get(uid);
        String imEntryAddr;
        if (routerUser == null
            || null == (imEntryAddr = routerUser.getImEntryAddr())) {
            return false;
        }
        RpcContext.getServiceContext().setAttachment(DubboConstant.IM_ENTRY_ADDR, imEntryAddr);
        return true;
    }

}
