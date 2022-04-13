package net.sopod.soim.das.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.UserDasService;
import net.sopod.soim.das.user.dao.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * UserDasServiceImpl
 *
 * @author tmy
 * @date 2022-04-13 23:30
 */
@DubboService
@AllArgsConstructor
public class UserDasServiceImpl implements UserDasService {

    private UserMapper userMapper;

    @Override
    public ImUser getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public ImUser getUserByAccount(String account) {
        LambdaQueryWrapper<ImUser> accountQuery = new QueryWrapper<ImUser>().lambda()
                .eq(ImUser::getUsername, account);
        return userMapper.selectOne(accountQuery);
    }

}
