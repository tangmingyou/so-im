package net.sopod.soim.client.handler.cmd;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.args.ArgsSend;
import net.sopod.soim.client.cmd.handler.CmdHandler;
import net.sopod.soim.client.logger.Console;
import net.sopod.soim.client.session.SoImSession;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.data.msg.chat.Chat;

/**
 * SendHandler
 *
 * @author tmy
 * @date 2022-04-28 14:03
 */
@Singleton
public class SendHandler implements CmdHandler<ArgsSend> {

    @Inject
    private SoImSession soImSession;

    @Override
    public ArgsSend newArgsInstance() {
        return new ArgsSend();
    }

    @Override
    public void handleArgs(ArgsSend args) {
        String message = Joiner.on(' ').join(args.getParameters());
        if (StringUtil.isEmpty(message)) {
            Console.info("请输入要发送的内容");
            return;
        }
        Chat.TextChat textChat = Chat.TextChat.newBuilder()
                .setMessage(message)
                .setReceiverAccount(args.getAccount())
                .setSender(soImSession.getUid())
                .build();
        soImSession.justSend(textChat);
    }

}
