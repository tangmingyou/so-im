package net.sopod.soim.logic.segmentid.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * SegmentId
 *
 * @author tmy
 * @date 2022-04-02 15:01
 */
@Data
@Accessors(chain = true)
@TableName("im_segment_id")
public class SegmentId {

    /** 业务标签 */
    @TableId(value = "biz_tag", type = IdType.INPUT)
    private String bizTag;

    /** 当前id值 */
    @TableField(value = "current_id")
    private Long currentId;

    /** 业务标签初始步长 */
    @TableField(value = "init_step")
    private Long initStep;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

    /** 更新时间 */
    @TableField(value = "update_time")
    private Date updateTime;

    /** 版本号 */
    @TableField(value = "version")
    private Long version;

}
