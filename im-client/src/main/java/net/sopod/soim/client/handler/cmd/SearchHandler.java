package net.sopod.soim.client.handler.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.args.ArgsSearch;
import net.sopod.soim.client.cmd.handler.CmdHandler;
import net.sopod.soim.client.session.SoImSession;
import net.sopod.soim.data.msg.user.AccountSearch;

/**
 * SearchHandler
 *
 * @author tmy
 * @date 2022-05-17 23:09
 */
@Singleton
public class SearchHandler implements CmdHandler<ArgsSearch> {

    @Inject
    private SoImSession soImSession;

    @Override
    public ArgsSearch newArgsInstance() {
        return new ArgsSearch();
    }

    @Override
    public void handleArgs(ArgsSearch args) {
        AccountSearch.ReqAccountSearch reqSearch = AccountSearch.ReqAccountSearch
                .newBuilder().setKeyword(args.getAccount()).build();
        soImSession.send(reqSearch);
    }

}
