package net.sopod.soim.router.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * RouterUser
 *
 * @author tmy
 * @date 2022-04-28 11:11
 */
@Data
@Accessors(chain = true)
public class RouterUser {

    private long uid;

    private String account;

    private Boolean isOnline;

    /** 在线时间戳 */
    private long onlineTime;

    private String imEntryAddr;

}
