package net.sopod.soim.logic.api.group.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * GroupUserInfo
 *
 * @author tmy
 * @date 2022-06-03 21:01
 */
@Data
@Accessors(chain = true)
public class GroupUserInfo implements Serializable {

    private static final long serialVersionUID = 6688468824170551058L;

    private Long uid;

    private String account;

    private String nickname;

    private Boolean online;

    // 最后活跃时间
    private Long lastActive;

    // 加群时间
    // private Long joinTime;

}
