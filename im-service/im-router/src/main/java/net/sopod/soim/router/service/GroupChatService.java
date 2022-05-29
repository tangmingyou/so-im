package net.sopod.soim.router.service;

/**
 * GroupChatService
 * 群组聊天消息
 * 1.发送者发送消息到 entry
 * 2.转发到 logic-group, logic-group 获取群成员id列表(缓存)，转发到对应的 im-router
 * 3.im-router 对在线用户发送消息（mq异步存储到db状态为已接收(状态标记在群聊单聊对象上)），离线用户状态为未接收
 * 4.用户登录时发送未接收[消息对象](单聊,群聊)列表
 * 5.查看消息历史，消息类型
 *
 * @author tmy
 * @date 2022-05-28 11:17
 */
public class GroupChatService {

}
