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
 * ImGroup 群聊
 *
 * @author tmy
 * @date 2022-05-28 15:14
 */
@Data
@Accessors(chain = true)
@TableName(LogicTables.IM_GROUP)
public class ImGroup implements Serializable {

    private static final long serialVersionUID = 2081139176340955425L;

    /** 群聊id */
    @TableId(value = "id")
    private Long id;

    /** 群主id */
    @TableField(value = "master_uid")
    private Long masterUid;

    /** 群聊名称 */
    @TableField(value = "group_name")
    private String groupName;

    /** 群聊人数限制 */
    @TableField(value = "user_limit")
    private String userLimit;

    /** 当前群聊人数 */
    @TableField(value = "user_num")
    private Date userNum;

    /** 创建时间 */
    @TableField(value = "create_time")
    private String createTime;

    /** 更新时间 */
    @TableField(value = "update_time")
    private Date updateTime;

}
