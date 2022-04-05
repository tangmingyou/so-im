package nets.sopod.soim.das.user.service;

import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.user.DasUserApplication;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * SegmentIdGeneratorTest
 *
 * @author tmy
 * @date 2022-04-04 09:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DasUserApplication.class)
public class SegmentIdGeneratorTest {

    @Resource
    private SegmentIdGenerator segmentIdGenerator;

    @Test
    public void test() {
        long begin = ImClock.millis();
        for (int i = 0; i < 100000; i++) {
            System.out.println(segmentIdGenerator.nextId("im_user"));
        }
        System.out.println(ImClock.millis() - begin + "ms");
    }

}
