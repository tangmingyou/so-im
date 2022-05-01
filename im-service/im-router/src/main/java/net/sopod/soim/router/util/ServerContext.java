package net.sopod.soim.router.util;

import net.sopod.soim.common.constant.DubboConstant;
import org.apache.dubbo.rpc.RpcContext;

/**
 * SessionContext
 *
 * @author tmy
 * @date 2022-05-01 23:08
 */
public class ServerContext {

    public static Long getContextUid() {
        String uidStr = RpcContext.getServerContext().getAttachment(DubboConstant.CTX_UID);
        return Long.valueOf(uidStr);
    }

}
