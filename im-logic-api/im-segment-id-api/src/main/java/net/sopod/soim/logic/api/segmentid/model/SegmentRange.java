package net.sopod.soim.logic.api.segmentid.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * SegmentDTO
 *
 * @author tmy
 * @date 2022-04-02 17:27
 */
@Data
@Accessors(chain = true)
public class SegmentRange implements Serializable {

    private static final long serialVersionUID = 4767453140231279111L;

    private long beginId;

    private long endId;

}
