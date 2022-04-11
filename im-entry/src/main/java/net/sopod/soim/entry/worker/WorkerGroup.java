package net.sopod.soim.entry.worker;

import com.google.protobuf.GeneratedMessageV3;
import net.sopod.soim.core.session.Account;
import net.sopod.soim.core.session.NetUser;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * dispatch -> worker * core_num -> disruptor 队列执行
 */
public class WorkerGroup {

    public static final int MAX_WORKER_SIZE = 16;

    private static Worker[] WORKERS;

    private static AtomicInteger counter;

    public static void init(int codeSize) {
        if (WORKERS != null) {
            return;
        }
        WORKERS = new Worker[codeSize];
        for (int i = 0; i < codeSize; i++) {
            WORKERS[i] = new Worker("group-worker-" + i);
        }
        counter = new AtomicInteger(-1);
    }

    public static Worker next() {
        counter.compareAndSet(Integer.MAX_VALUE, -1);
        return WORKERS[counter.incrementAndGet() % WORKERS.length];
    }

    public static void publish(NetUser netUser, GeneratedMessageV3 message) {
        next().execute(() -> {

        });
    }

    public static void publish(Account netUser, GeneratedMessageV3 message) {

    }

    public static void shutdown() {
        for (Worker worker : WORKERS) {
            worker.shutdown();
        }
    }

}
