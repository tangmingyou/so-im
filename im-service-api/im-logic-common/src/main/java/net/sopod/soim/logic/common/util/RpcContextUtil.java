package net.sopod.soim.logic.common.util;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.common.util.StringUtil;
import org.apache.dubbo.rpc.RpcContext;

/**
 * RpcContextUtil
 *
 * @author tmy
 * @date 2022-05-04 22:44
 */
public class RpcContextUtil {

    public static void setContextUid(Long uid) {
        setContextUid(StringUtil.toString(uid));
    }

    public static void setContextUid(String uid) {
        RpcContext.getServiceContext().setAttachment(DubboConstant.CTX_UID, uid);
    }

    public static String getContextUid() {
        return RpcContext.getServiceContext().getAttachment(DubboConstant.CTX_UID);
    }

    public static Long getContextUidNum() {
        String uid = getContextUid();
        return uid == null ? null : Long.valueOf(uid);
    }

}
