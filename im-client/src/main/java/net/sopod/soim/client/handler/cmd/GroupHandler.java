package net.sopod.soim.client.handler.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.args.ArgsGroup;
import net.sopod.soim.client.cmd.handler.CmdHandler;
import net.sopod.soim.client.logger.Console;
import net.sopod.soim.client.session.SoImSession;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.data.msg.common.Res;
import net.sopod.soim.data.msg.group.Group;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * GroupHandler
 *
 * @author tmy
 * @date 2022-06-05 09:35
 */
@Singleton
public class GroupHandler implements CmdHandler<ArgsGroup> {

    @Inject
    private SoImSession soImSession;

    @Override
    public ArgsGroup newArgsInstance() {
        return new ArgsGroup();
    }

    @Override
    public void handleArgs(ArgsGroup args) {
        if (args.getGroupNameCreate() != null) {
            this.handleGroupCreate(args.getGroupNameCreate());

        } else if (args.getGroupNameSearch() != null) {
            this.handleGroupSearch(args.getGroupNameSearch());

        } else if (args.getJoinGroupId() != null) {
            this.handleGroupJoin(args.getJoinGroupId());

        } else if (args.getUsersGroupId() != null) {
            this.handleGroupUsers(args.getUsersGroupId());
        } else {
            this.handleUserGroups();
        }
    }

    /**
     * 创建群聊
     */
    private void handleGroupCreate(String groupName) {
        if (StringUtil.isBlank(groupName)) {
            Console.error("群聊名称不能为空");
            return;
        }
        Group.ReqCreateGroup req = Group.ReqCreateGroup.newBuilder()
                .setGroupName(groupName)
                .build();
        CompletableFuture<Res.ResState> future = soImSession.send(req);
        Res.ResState res = future.join();
        String message = res.getMessage();
        Console.info(message);
    }

    /**
     * 群聊搜索
     */
    private void handleGroupSearch(String groupName) {
        if (StringUtil.isBlank(groupName)) {
            Console.error("群聊名称不能为空");
            return;
        }
        Group.ReqSearchGroup req = Group.ReqSearchGroup.newBuilder()
                .setGroupName(groupName)
                .build();
        CompletableFuture<Group.ResSearchGroup> future = soImSession.send(req);
        Group.ResSearchGroup res = future.join();
        List<Group.GroupInfo> groupsList = res.getGroupsList();

        List<String> userLines = groupsList.stream()
                .map(g -> g.getGid() + " | " +
                        g.getGroupName() + " | " +
                        g.getUserLimit() + " | " +
                        g.getUserNum() + " | " +
                        ImClock.millis2time(g.getCreateTime()))
                .collect(Collectors.toList());
        Console.logList("群聊搜索结果", userLines);
    }

    /**
     * 加入群聊
     */
    private void handleGroupJoin(Integer groupId) {
        Group.ReqJoinGroup req = Group.ReqJoinGroup.newBuilder()
                .setGid(groupId)
                .build();
        CompletableFuture<Res.ResState> future = soImSession.send(req);
        Res.ResState res = future.join();
        String message = res.getMessage();
        Console.info(message);
    }


    /**
     * 群聊用户列表
     */
    private void handleGroupUsers(Integer groupId) {
        Group.ReqGroupUsers req = Group.ReqGroupUsers.newBuilder()
                .setGid(groupId)
                .build();
        CompletableFuture<Group.ResGroupUsers> future = soImSession.send(req);
        Group.ResGroupUsers res = future.join();
        List<Group.UserInfo> usersList = res.getUsersList();

        List<String> userLines = usersList.stream()
                .map(u -> u.getUid() + "|" + u.getAccount() + "|" +
                        u.getNickname() + "|" +
                        u.getOnline() + "|" +
                        ImClock.millis2time(u.getLastActive())
                ).collect(Collectors.toList());
        Console.logList("群用户列表", userLines);
    }

    private void handleUserGroups() {
        Group.ReqUserGroups req = Group.ReqUserGroups.newBuilder().build();
        CompletableFuture<Group.ResUserGroups> future = soImSession.send(req);
        Group.ResUserGroups res = future.join();
        List<Group.GroupInfo> groupsList = res.getGroupsList();
        List<String> userLines = groupsList.stream()
                .map(g -> g.getGid() + " | " +
                        g.getGroupName() + " | " +
                        g.getUserLimit() + " | " +
                        g.getUserNum() + "/" +
                        g.getUserLimit() + " | " +
                        g.getMasterUid() + " | " +
                        ImClock.millis2time(g.getCreateTime()))
                .collect(Collectors.toList());
        Console.logList("我的群聊", userLines);
    }

}
