package net.sopod.soim.das.group.api.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * GroupUser0
 *
 * @author tmy
 * @date 2022-06-03 21:24
 */
@Data
@Accessors(chain = true)
public class GroupUser_0 implements Serializable {

    private static final long serialVersionUID = 2795983825784323892L;

    private Long uid;

    private Long lastActive;

    private transient Boolean isOnline;

}
