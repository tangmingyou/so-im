package net.sopod.soim.entry.worker;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.netty.util.concurrent.DefaultThreadFactory;
import net.sopod.soim.common.util.ImClock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Worker
 *
 * @author tmy
 * @date 2022-04-11 17:05
 */
public class DisruptorTest {
    public static class LongEvent {
        int id;
        private Long value;
        public Long getValue() {
            return value;
        }
        public void setValue(Long value) {
        this.value = value;
    }
    }
    public static class LongEventFactory implements EventFactory<LongEvent> {
        private static final AtomicInteger counter = new AtomicInteger(1);
        @Override
        public LongEvent newInstance() {
            int count = counter.getAndIncrement();
            System.out.println("instance:"+ count);
            LongEvent event = new LongEvent();
            event.id = count;
            return event;
        }
    }
    public static class LongEventHandler implements EventHandler<LongEvent> {
        @Override
        public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
            System.out.println("消费者:"+event.id + "," + sequence + "," + event.getValue() + "," + Thread.currentThread().getName());
            Thread.sleep(20);
            // event值置空，回收内存
            event.setValue(null);
        }
    }
    public static void main(String[] args) throws InterruptedException, InsufficientCapacityException {
        LongEventFactory eventFactory = new LongEventFactory();
        int ringBufferSize = 128;//64 * 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(
                eventFactory,
                ringBufferSize,
                new DefaultThreadFactory("disruptor"),
                ProducerType.SINGLE,
                new BlockingWaitStrategy()
        );
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();

        // 7.创建RingBuffer容器
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        for (long i = 0; i < 1000; i++) {
            long start = ImClock.millis();
            // 没有空闲位置时会阻塞
            long sequence = ringBuffer.tryNext();
            //long sequence = ringBuffer.next();
            LongEvent longEvent = ringBuffer.get(sequence);
            longEvent.setValue(i);
            Thread.sleep(10);
            ringBuffer.publish(sequence);
            System.out.println("生产者:" + i + "," + (ImClock.millis() - start) + "ms");
        }

        Thread.sleep(5000);
        disruptor.shutdown();
    }
}
