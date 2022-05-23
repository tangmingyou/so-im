package net.sopod.soim.das.user.api.service;

import net.sopod.soim.das.user.api.model.entity.ImUser;

import java.util.List;

/**
 * FriendDas
 *
 * @author tmy
 * @date 2022-05-23 15:45
 */
public interface FriendDas {

    /**
     * 新增好友
     *
     * @param uid 用户id
     * @param fid 好友id
     * @return 影响行数
     */
    int insert(Long uid, Long fid);

    /**
     * 好友是否存在
     *
     * @param uid 用户id
     * @param fid 好友id
     * @return 影响行数
     */
    Boolean isExists(Long uid, Long fid);

    /**
     * 删除好友
     *
     * @param uid 用户id
     * @param fid 好友id
     * @return 影响行数
     */
    int remove(Long uid, Long fid);

    /**
     * 查询所有好友
     *
     * @param uid 主键
     * @return 好友列表
     */
    List<ImUser> queryAllFriend(Long uid);

}
