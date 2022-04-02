package net.sopod.soim.logic.segmentid.api.service;

import net.sopod.soim.logic.segmentid.api.model.dto.NextSegmentParam;
import net.sopod.soim.logic.segmentid.api.model.dto.SegmentDTO;

/**
 * SegmentIdService
 *
 * @author tmy
 * @date 2022-04-02 23:07
 */
public interface SegmentIdService {

    SegmentDTO nextSegmentId(String bizTag);

    SegmentDTO nextSegmentId(String bizTag, Long step);

}
