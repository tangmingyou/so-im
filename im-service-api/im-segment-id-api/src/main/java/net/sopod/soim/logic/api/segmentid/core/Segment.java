package net.sopod.soim.logic.api.segmentid.core;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Segment
 *
 * @author tmy
 * @date 2022-04-03 23:12
 */
public class Segment {

    private final AtomicLong atomicId;

    private final long end;

    public Segment(long begin, long end) {
        atomicId = new AtomicLong(begin);
        this.end = end;
    }

    public boolean hasNext() {
        return this.atomicId.get() <= this.end;
    }

    public long next() {
        long id = this.atomicId.getAndIncrement();
        if (id > this.end) {
            throw new IllegalStateException("id最大值超过范围");
        }
        return id;
    }

}
