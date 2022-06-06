package net.sopod.soim.das.group.api.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * GroupView
 *
 * @author tmy
 * @date 2022-06-06 21:04
 */
@Data
@Accessors(chain = true)
public class GroupView implements Serializable {

    private static final long serialVersionUID = -9110996998635374890L;

    private Long gid;

    /** 群主id */
    private Long masterUid;

    private String gName;

    private Integer userLimit;

    private Integer userNum;

    private Integer unreadNum;

    private Date createTime;

}
