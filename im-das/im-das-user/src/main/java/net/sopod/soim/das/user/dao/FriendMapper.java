package net.sopod.soim.das.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.sopod.soim.das.user.api.model.entity.ImFriend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FriendMapper
 *
 * @author tmy
 * @date 2022-05-22 23:24
 */
@Repository
public interface FriendMapper extends BaseMapper<ImFriend> {

    List<Long> selectAllFriendId(@Param("uid") Long uid);

}
