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
 * ImMessage 聊天消息
 *
 * @author tmy
 * @date 2022-05-28 14:56
 */
@Data
@Accessors(chain = true)
@TableName(LogicTables.IM_MESSAGE)
public class ImMessage implements Serializable {

    private static final long serialVersionUID = 7374196343496533922L;

    /** id */
    @TableId(value = "id")
    private Long id;

    /** 好友关系id(分片) */
    @TableField(value = "friend_id")
    private Long friendId;

    /** 发送人id */
    @TableField(value = "sender")
    private Long sender;

    /** 接受者id */
    @TableField(value = "receiver")
    private Long receiver;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

    /** 消息内容 */
    @TableField(value = "content")
    private String content;

}