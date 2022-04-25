package net.sopod.soim.client.cmd;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.util.List;

/**
 * ArgsLogin
 *
 * @author tmy
 * @date 2022-04-25 10:42
 */
@Data
public class ArgsLogin {

    @Parameter
    private List<String> parameters;

    @Parameter(names = {"-u", "-account"}, required = true, description = "账号")
    private String account;

    @Parameter(names = {"-p", "-password"}, required = true, description = "密码", password = true)
    private String password;

}
