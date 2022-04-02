package net.sopod.soim.logic.segmentid.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * SegmentDTO
 *
 * @author tmy
 * @date 2022-04-02 17:27
 */
@Data
@Accessors(chain = true)
public class SegmentDTO {

    private long beginId;

    private long endId;

}
