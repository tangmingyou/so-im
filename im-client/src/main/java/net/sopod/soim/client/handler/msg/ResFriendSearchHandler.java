package net.sopod.soim.client.handler.msg;

import com.google.inject.Singleton;
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.client.session.MessageHandler;
import net.sopod.soim.data.msg.user.AccountSearch;
import net.sopod.soim.data.msg.user.UserGroup;
import net.sopod.soim.data.msg.user.UserMsg;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ResFriendSearchHandler
 *
 * @author tmy
 * @date 2022-05-17 23:41
 */
@Singleton
public class ResFriendSearchHandler implements MessageHandler<AccountSearch.ResAccountSearch> {

    @Override
    public void handleMsg(AccountSearch.ResAccountSearch res) {
        List<UserMsg.UserInfo> usersList = res.getUsersList();
        List<String> userLines = usersList.stream()
                .map(u -> u.getUid() + "|" + u.getAccount() + "|" + u.getNickname())
                .collect(Collectors.toList());
        Logger.logList("搜索结果", userLines);
    }

}
