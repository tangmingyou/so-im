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

}
