package net.sopod.soim.das.user.api.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * ImUser
 *
 * @author tmy
 * @date 2022-04-01 17:07
 */
@Data
@Accessors(chain = true)
@TableName("im_user")
public class ImUser {

    /** id */
    @TableId(value = "id", type= IdType.INPUT)
    private Long id;

    /** 用户名 */
    @TableField(value = "username")
    private String username;

    /** 密码 */
    @TableField(value = "password")
    private String password;

    /** 手机号 */
    @TableField(value = "phone")
    private String phone;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

    /** 最后登录时间 */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;

    /** 最后活跃时间 */
    @TableField(value = "last_active_time")
    private Date lastActiveTime;

}
