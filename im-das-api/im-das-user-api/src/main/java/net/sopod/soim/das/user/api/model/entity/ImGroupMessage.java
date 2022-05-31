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
 * ImGroupMessage 群聊天消息
 *
 * @author tmy
 * @date 2022-05-28 15:17
 */
@Data
@Accessors(chain = true)
@TableName(LogicTables.IM_GROUP_MESSAGE)
public class ImGroupMessage implements Serializable {

    private static final long serialVersionUID = -509462521791459558L;

    /** id */
    @TableId(value = "id")
    private Long id;

    /** 群聊id */
    @TableField(value = "group_id")
    private Long groupId;

    /** 发送者id */
    @TableField(value = "sender")
    private Long sender;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

    /** 消息内容 */
    @TableField(value = "content")
    private String content;

}
