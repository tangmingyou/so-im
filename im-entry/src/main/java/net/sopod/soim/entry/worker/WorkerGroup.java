package net.sopod.soim.entry.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * dispatch -> worker * core_num -> disruptor 队列执行
 */
public class WorkerGroup {

    private static final Logger logger = LoggerFactory.getLogger(WorkerGroup.class);

    public static final int MAX_WORKER_SIZE = 16;

    private static Worker[] WORKERS;

    private static AtomicInteger counter;

    public static void init(int coreSize) {
        if (WORKERS != null) {
            logger.warn("worker group duplicate init, current worker size {}, param size {}", WORKERS.length, coreSize);
            return;
        }
        WORKERS = new Worker[coreSize];
        for (int i = 0; i < coreSize; i++) {
            WORKERS[i] = new Worker("group-worker-" + i);
        }
        counter = new AtomicInteger(-1);
        logger.info("worker group initialed, worker size {}", coreSize);
    }

    public static Worker next() {
        counter.compareAndSet(Integer.MAX_VALUE, -1);
        return WORKERS[counter.incrementAndGet() % WORKERS.length];
    }

    public static void shutdown() {
        for (Worker worker : WORKERS) {
            worker.shutdown();
        }
    }

}
