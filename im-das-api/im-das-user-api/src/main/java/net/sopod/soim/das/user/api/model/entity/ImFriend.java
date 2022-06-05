package net.sopod.soim.das.user.api.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import net.sopod.soim.das.common.config.LogicTables;

import java.io.Serializable;
import java.util.Date;

/**
 * ImFriend
 * 好友关系表
 *
 * @author tmy
 * @date 2022-05-22 23:22
 */
@Data
@Accessors(chain = true)
@TableName(LogicTables.IM_FRIEND)
public class ImFriend implements Serializable {

    private static final long serialVersionUID = -4703347353944742873L;

    /**  */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /** 关系id(消息存储路由分片) */
    @TableField(value = "relation_id")
    private Long relationId;

    /** 用户id */
    @TableField(value = "uid")
    private Long uid;

    /** 好友id */
    @TableField(value = "fid")
    private Long fid;

    /**
     * 状态:0删除1正常
     * TODO 2临时好友(群单聊)
     **/
    @TableField(value = "status")
    private Integer status;

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
