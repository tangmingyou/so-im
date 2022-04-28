package net.sopod.soim.client.cmd;

import net.sopod.soim.client.cmd.handler.CmdHandler;
import net.sopod.soim.client.handler.cmd.ExitHandler;
import net.sopod.soim.client.handler.cmd.LoginHandler;
import net.sopod.soim.client.handler.cmd.SendHandler;
import net.sopod.soim.client.handler.cmd.UsersHandler;

/**
 * Commands
 *
 * @author tmy
 * @date 2022-04-25 10:51
 */
public enum CmdEnum {

    /** 登录 */
    login(LoginHandler.class),

    /** 发送消息 */
    send(SendHandler.class),

    /** 在线用户列表 */
    users(UsersHandler.class),

    /** 用户分组列表 */
    groups,

    help,

    /** 退出 */
    exit(ExitHandler.class);

    private Class<? extends CmdHandler<?>> handlerClass;

    CmdEnum() {

    }

    CmdEnum(Class<? extends CmdHandler<?>> handlerClass) {
        this.handlerClass = handlerClass;
    }

    public Class<?extends CmdHandler<?>> getHandlerClass() {
        return this.handlerClass;
    }

    public static CmdEnum getValue(String cmdName) {
        for (CmdEnum value : values()) {
            if (value.name().equals(cmdName)) {
                return value;
            }
        }
        return null;
    }

}
