package net.sopod.soim.logic.segmentid.test;

import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.logic.segmentid.SegmentIdApplication;
import net.sopod.soim.logic.api.segmentid.model.SegmentRange;
import net.sopod.soim.logic.api.segmentid.service.SegmentIdService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

/**
 * SegmentIdApplicationTest
 *
 * @author tmy
 * @date 2022-04-03 00:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SegmentIdApplication.class)
public class SegmentIdApplicationTest {

    @DubboReference
    private SegmentIdService segmentIdService;

    @Test
    public void test1() throws InterruptedException {
        CountDownLatch countDown = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final int idx = i;
            new Thread(() -> {
                try {
                    long begin = ImClock.millis();
                    SegmentRange segment = segmentIdService.nextSegmentId("im-user");
                    System.out.println(idx + segment.toString() + (ImClock.millis() - begin));
                }catch (Exception e) {
                    System.out.println(idx + "fail:" + e.getMessage());
                } finally {
                    countDown.countDown();
                }
            }).start();
        }
        countDown.await();
    }

}
