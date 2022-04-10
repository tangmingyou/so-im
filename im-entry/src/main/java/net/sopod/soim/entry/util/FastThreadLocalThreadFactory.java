package net.sopod.soim.entry.util;

import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class FastThreadLocalThreadFactory implements ThreadFactory {
    private String name;
    private int priority;
    private AtomicInteger counter;

    public FastThreadLocalThreadFactory(String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.counter = new AtomicInteger();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        FastThreadLocalThread thread = new FastThreadLocalThread(
                runnable,
                String.format(name, counter.incrementAndGet())
        );
        thread.setPriority(priority);
        return thread;
    }
}
