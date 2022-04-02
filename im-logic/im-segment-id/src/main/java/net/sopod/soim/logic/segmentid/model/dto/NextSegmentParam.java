package net.sopod.soim.logic.segmentid.model.dto;

import lombok.Data;

/**
 * NextSegmentParam
 *
 * @author tmy
 * @date 2022-04-02 16:51
 */
@Data
public class NextSegmentParam {

    /** 业务标签 */
    private String bizTag;

    /** 步长，为空则使用数据库默认 */
    private Long step;

}
