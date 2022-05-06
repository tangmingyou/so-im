package net.sopod.soim.router.cache;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.sopod.soim.router.datasync.DataSync;
import net.sopod.soim.router.datasync.annotation.SyncIgnore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * RouterUser
 *
 * @author tmy
 * @date 2022-04-28 11:11
 */
@Data
@Accessors(chain = true)
public class RouterUser implements DataSync {

    public static final int a = 1;

    private long uid;

    private String account;

    private Boolean isOnline;

    /** 在线时间戳 */
    private long onlineTime;

    private String imEntryAddr;

    @SyncIgnore
    public RouterUser setUid(long uid) {
        this.uid = uid;
        return this;
    }

}
