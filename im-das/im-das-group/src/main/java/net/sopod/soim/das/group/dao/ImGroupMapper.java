package net.sopod.soim.das.group.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.sopod.soim.das.group.api.model.entity.ImGroup;

/**
 * ImGroupMapper
 *
 * @author tmy
 * @date 2022-05-31 9:59
 */
public interface ImGroupMapper extends BaseMapper<ImGroup> {

    Long selectExistsGroupByName(String groupName);

}
