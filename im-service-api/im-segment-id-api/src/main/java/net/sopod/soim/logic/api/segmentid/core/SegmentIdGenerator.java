package net.sopod.soim.logic.api.segmentid.core;

import net.sopod.soim.logic.api.segmentid.model.SegmentRange;
import net.sopod.soim.logic.api.segmentid.service.SegmentIdService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * SegmentGenerator
 *
 * @author tmy
 * @date 2022-04-03 23:21
 */
public class SegmentIdGenerator {

    private final SegmentIdService segmentIdService;

    private final Map<String, LinkedList<Segment>> segmentsMap;

    public SegmentIdGenerator(SegmentIdService segmentIdService) {
        this.segmentIdService = segmentIdService;
        segmentsMap = new HashMap<>();
    }

    /**
     * TODO 动态增加步长
     * TODO 剩余数一半时, 异步更新 segment
     * @param bizTag 业务标签，同一业务标签生成id唯一
     * @return id
     */
    public long nextId(String bizTag) {
        if (bizTag == null || bizTag.isEmpty()) {
            throw new IllegalStateException("bizTag can not be empty!");
        }
        synchronized (bizTag.intern()) {
            LinkedList<Segment> segments = segmentsMap.computeIfAbsent(bizTag, tag -> {
                LinkedList<Segment> nextSegments = new LinkedList<>();
                SegmentRange range = segmentIdService.nextSegmentId(bizTag);
                Segment segment = new Segment(range.getBeginId(), range.getEndId());
                nextSegments.add(segment);
                return nextSegments;
            });
            Segment segment = segments.getFirst();
            if (!segment.hasNext()) {
                SegmentRange nextRange = segmentIdService.nextSegmentId(bizTag);
                segment = new Segment(nextRange.getBeginId(), nextRange.getEndId());
                segments.removeFirst();
                segments.add(segment);
            }
            return segment.next();
        }
    }

}
