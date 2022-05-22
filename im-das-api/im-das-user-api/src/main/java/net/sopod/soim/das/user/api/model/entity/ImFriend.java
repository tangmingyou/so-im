package net.sopod.soim.das.user.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

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
@TableName("im_friend")
public class ImFriend implements Serializable {

    private static final long serialVersionUID = -4703347353944742873L;

    /**  */
    @TableId(value = "id")
    private Long id;

    /** 用户id */
    @TableField(value = "uid")
    private Long uid;

    /** 好友id */
    @TableField(value = "fid")
    private Long fid;

    /** 状态:0删除1正常 */
    @TableField(value = "status")
    private Integer status;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

}
