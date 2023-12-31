package net.sopod.soim.entry.worker;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.netty.FastThreadLocalThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * dispatch -> worker * core_num -> disruptor 队列执行
 */
public class Worker implements EventHandler<TaskEvent>, EventFactory<TaskEvent> {

    private static final Logger logger = LoggerFactory.getLogger(Worker.class);

    private Disruptor<TaskEvent> disruptor;

    private RingBuffer<TaskEvent> ringBuffer;

    private ExecutorService executor;

    public Worker(String workerThread) {
        // Executors.newSingleThreadExecutor(new FastThreadLocalThreadFactory(workerThread, Thread.MAX_PRIORITY));
        this.executor = new ThreadPoolExecutor(
                1,
                1,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new FastThreadLocalThreadFactory(workerThread, Thread.MAX_PRIORITY)
        );
        this.disruptor = new Disruptor<>(
                this,
                16 * 1024,
                executor,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());
        this.disruptor.handleEventsWith(this);
        this.disruptor.start();
        this.ringBuffer = disruptor.getRingBuffer();
    }

    public void execute(Runnable runnable) {
        long next = ringBuffer.next();
        TaskEvent taskEvent = ringBuffer.get(next);
        taskEvent.setTask(runnable);
        ringBuffer.publish(next);
    }

    public void shutdown() {
        this.disruptor.shutdown();
        this.executor.shutdown();
    }

    @Override
    public void onEvent(TaskEvent taskEvent, long sequence, boolean endOfBatch) throws Exception {
        try {
            long start = ImClock.millis();
            taskEvent.getTask().run();
            long time = ImClock.millis() - start;
            if (time > 100) {
                logger.info("任务执行时间过长: {}", time);
            }
        } catch (Exception e) {
            logger.error("任务执行失败:", e);
        }
        // 事件对象不会释放，将数据置空
        taskEvent.setTask(null);
    }

    @Override
    public TaskEvent newInstance() {
        return new TaskEvent();
    }

    public <T> void whenCompleteAsync(CompletableFuture<T> future,
                                      BiConsumer<? super T, ? super Throwable> action) {
//        BiConsumer<? super T, ? super Throwable> wrapper = (data, err) -> {
//            try {
//                action.accept(data, err);
//            }catch (Exception e) {
//                logger.error("future执行失败: ", e);
//            }
//        };
        future.whenCompleteAsync(action, executor)
                .exceptionally(err -> {
                    logger.error("future执行失败: ", err);
                    return null;
                });
    }

}
