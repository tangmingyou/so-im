package net.sopod.soim.client.cmd;

import com.beust.jcommander.Parameter;
import lombok.Data;

/**
 * ArgsLogin
 *
 * @author tmy
 * @date 2022-04-25 10:42
 */
@Data
public class ArgsLogin {

    @Parameter(names = {"-u", "-account"}, description = "账号")
    private String account;

    @Parameter(names = {"-p", "-password"}, description = "密码", password = true)
    private String password;

}
