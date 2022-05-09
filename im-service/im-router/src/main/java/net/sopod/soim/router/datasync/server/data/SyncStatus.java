package net.sopod.soim.router.datasync.server.data;

/**
 * SyncStatus
 *
 * @author tmy
 * @date 2022-05-10 00:18
 */
public enum SyncStatus {

    NONE, // 无动作
    FULL_SYNCING, // 数据全量同步中
    SYNC_CHANGE_LOG, // 全量同步结束，同步更改日志中
    SYNC_FINISH, // 数据同步结束，不在推送

}
