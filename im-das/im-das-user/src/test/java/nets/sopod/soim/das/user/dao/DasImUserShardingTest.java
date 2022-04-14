package nets.sopod.soim.das.user.dao;

import com.google.common.base.Preconditions;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.user.DasUserApplication;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.config.LogicTables;
import net.sopod.soim.das.user.dao.UserMapper;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * DasUserApplication
 *
 * @author tmy
 * @date 2022-04-01 14:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DasUserApplication.class)
public class DasImUserShardingTest {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private SegmentIdGenerator segmentIdGen;

    @Test
    public void test() {
        long userId = segmentIdGen.nextId(LogicTables.IM_USER);
        ImUser user1 = new ImUser()
                .setId(userId)
                .setStatus(1)
                .setAccount("poros")
                .setNickname("波罗斯")
                .setPassword("123456")
                .setPhone("17882451902")
                .setCreateTime(ImClock.date());
        userMapper.insert(user1);

        ImUser imUser = userMapper.selectById(userId);
        Preconditions.checkState(imUser != null
                && user1.getAccount().equals(imUser.getAccount())
                && user1.getPhone().equals(imUser.getPhone())
        );
    }

}
