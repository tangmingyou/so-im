package net.sopod.soim.das.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import net.sopod.soim.common.constant.LogicConsts;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.das.user.dao.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * UserDasServiceImpl
 *
 * @author tmy
 * @date 2022-04-13 23:30
 */
@DubboService
@AllArgsConstructor
public class UserDasImpl implements UserDas {

    private UserMapper userMapper;

    @Override
    public ImUser getUserById(Long id) {
        LambdaQueryWrapper<ImUser> userQuery = new QueryWrapper<ImUser>().lambda()
                .eq(ImUser::getId, id)
                .eq(ImUser::getStatus, LogicConsts.STATUS_NORMAL);
        return userMapper.selectOne(userQuery);
    }

    @Override
    public ImUser getNormalUserByAccount(String account) {
        LambdaQueryWrapper<ImUser> accountQuery = new QueryWrapper<ImUser>().lambda()
                .eq(ImUser::getAccount, account)
                .eq(ImUser::getStatus, LogicConsts.STATUS_NORMAL);
        return userMapper.selectOne(accountQuery);
    }

    public List<ImUser> searchImUser(String keyword, Integer limit) {
        Page<ImUser> page = new Page<>();
        page.setCurrent(1).setSize(limit);
        LambdaQueryWrapper<ImUser> userQuery = new QueryWrapper<ImUser>().lambda()
                .likeRight(ImUser::getAccount, keyword);
        Page<ImUser> imUserPage = userMapper.selectPage(page, userQuery);
        return imUserPage.getRecords();
    }


}
