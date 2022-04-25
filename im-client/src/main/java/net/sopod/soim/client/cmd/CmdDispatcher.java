package net.sopod.soim.client.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.handler.CmdHandler;
import net.sopod.soim.client.logger.Logger;

/**
 * CommandDispatcher
 *
 * auth -u prometheus -p 123456
 * send zeus hello
 * send prometheus hi!
 * users
 * groups
 *
 *
 * @author tmy
 * @date 2022-04-25 10:28
 */
@Singleton
public class CmdDispatcher {

    private final Injector injector;

    @Inject
    public CmdDispatcher(Injector injector) {
        this.injector = injector;
    }

    @SuppressWarnings("unchecked")
    public void dispatchCmd(String cmdStr) {
        String[] cmdArgs = cmdStr.trim().split("[ \t]+");
        String cmd = cmdArgs[0];
        String[] args = new String[cmdArgs.length - 1];
        if (args.length > 0) {
            System.arraycopy(cmdArgs, 1, args, 0, args.length);
        }

        CmdEnum cmdEnum = CmdEnum.getValue(cmd);
        if (cmdEnum == null) {
            Logger.error("未知指令: {}", cmd);
            return;
        }
        Class<? extends CmdHandler<?>> handlerClass = cmdEnum.getHandlerClass();
        CmdHandler<?> cmdHandler = injector.getInstance(handlerClass);
        Object argsInstance = cmdHandler.newArgsInstance();
        try {
            JCommander.newBuilder()
                    .addObject(argsInstance)
                    .build()
                    .parse(args);
        } catch (ParameterException e) {
            String message = e.getMessage();
            this.parseParameterExceptionMessage(message);
            return;
        }
        ((CmdHandler<Object>)cmdHandler).handleArgs(argsInstance);
    }

    private void parseParameterExceptionMessage(String message) {
        Logger.error(message);
    }

}
