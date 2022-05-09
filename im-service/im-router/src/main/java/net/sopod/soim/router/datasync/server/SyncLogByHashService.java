package net.sopod.soim.router.datasync.server;

import io.netty.util.AttributeKey;

/**
 * SyncLogPushService
 *
 * @author tmy
 * @date 2022-05-10 00:30
 */

public class SyncLogByHashService {

    public static final AttributeKey<SyncLogByHashService> ATTR_KEY = AttributeKey
            .valueOf(SyncLogByHashService.class, "SYNC_LOG_BY_HASH_SERVICE");

    private final String newNodeAddr;

    public SyncLogByHashService(String newNodeAddr) {
        this.newNodeAddr = newNodeAddr;

    }

    public void startPush() {
        // TODO 开始数据推送

    }

}
