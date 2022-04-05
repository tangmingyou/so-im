package nets.sopod.soim.das.user.dao;

import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.user.DasUserApplication;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test
    public void test() {
        ImUser user1 = new ImUser()
                .setId(5L)
                .setUsername("野孩子5")
                .setPassword("123456")
                .setPhone("17882451908")
                .setCreateTime(ImClock.date());
        userMapper.insert(user1);
    }

}
