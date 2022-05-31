package net.sopod.soim.das.user.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import net.sopod.soim.das.user.api.config.LogicTables;

import java.io.Serializable;
import java.util.Date;

/**
 * ImGroupUser 群聊用户列表
 *
 * @author tmy
 * @date 2022-05-28 15:15
 */
@Data
@Accessors(chain = true)
@TableName(LogicTables.IM_GROUP_USER)
public class ImGroupUser implements Serializable {

    private static final long serialVersionUID = -1236982266782872316L;

    /** id */
    @TableId(value = "id")
    private Long id;

    /** 群聊id */
    @TableField(value = "group_id")
    private Long groupId;

    /** 用户id */
    @TableField(value = "uid")
    private Long uid;

    /** 群未读消息数量 */
    @TableField(value = "unread_num")
    private Integer unreadNum;

    /** 群未读消息id偏移量(大于等于该id的都未读) */
    @TableField(value = "unread_offset_id")
    private Long unreadOffsetId;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

}
