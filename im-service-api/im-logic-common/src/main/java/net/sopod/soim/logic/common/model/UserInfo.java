package net.sopod.soim.logic.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * UserInfo
 *
 * @author tmy
 * @date 2022-04-28 11:10
 */
@Data
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -979827759390120082L;

    private Long uid;

    private String account;

    private String nickname;

    private Boolean isOnline;

}
