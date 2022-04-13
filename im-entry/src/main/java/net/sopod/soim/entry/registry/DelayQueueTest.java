package net.sopod.soim.entry.registry;

import net.sopod.soim.common.util.ImClock;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueueTest
 *
 * @author tmy
 * @date 2022-04-13 16:41
 */
public class DelayQueueTest {

    public static class DelayEle implements Delayed  {

        private final TimeUnit unit;

        private final long time;

        public DelayEle(long delay, TimeUnit unit) {
            this.unit = unit;
            this.time = ImClock.millis() + unit.toMillis(delay);
        }

        @Override
        public long getDelay(TimeUnit timeUnit) {
            System.out.println("[get delay]..." + this.time);
            return time - ImClock.millis();
        }

        @Override
        public int compareTo(Delayed delayed) {
            return Long.compare(this.time, delayed.getDelay(unit));
        }

        public long getTime() {
            return this.time;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Delayed> delayQueue = new DelayQueue<>();
        DelayEle ele1 = new DelayEle(10, TimeUnit.SECONDS);
        DelayEle ele2 = new DelayEle(15, TimeUnit.SECONDS);
        DelayEle ele3 = new DelayEle(20, TimeUnit.SECONDS);
        delayQueue.add(ele1);
        delayQueue.add(ele2);
        delayQueue.add(ele3);
        for (int i = 0; i < 3;) {
            DelayEle ele = (DelayEle)delayQueue.poll();
            if (ele != null) {
                System.out.println(ele.getTime());
                i ++;
            }
            Thread.sleep(1000);
        }

    }

}
