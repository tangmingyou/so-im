package net.sopod.soim.das.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import org.springframework.stereotype.Repository;

/**
 * UserMapper
 *
 * @author tmy
 * @date 2022-04-01 17:00
 */
@Repository
public interface UserMapper extends BaseMapper<ImUser> {

}
