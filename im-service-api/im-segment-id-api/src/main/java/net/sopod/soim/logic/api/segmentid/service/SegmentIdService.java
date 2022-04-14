package net.sopod.soim.logic.api.segmentid.service;

import net.sopod.soim.logic.api.segmentid.model.SegmentRange;

/**
 * SegmentIdService
 *
 * @author tmy
 * @date 2022-04-02 23:07
 */
public interface SegmentIdService {

    SegmentRange nextSegmentId(String bizTag);

    SegmentRange nextSegmentId(String bizTag, Long step);

    interface Version {

        /**
         * 基本功能：获取业务标签对应的分段id范围
         * 大版本向下兼容
         */
        String v1 = "1";

    }

}
