package net.sopod.soim.client.cmd.args;

import com.beust.jcommander.Parameter;

import java.util.List;

/**
 * ArgsSearch
 *
 * @author tmy
 * @date 2022-05-17 23:10
 */
public class ArgsSearch {

    @Parameter
    private List<String> parameters;

    @Parameter(names = {"-u", "-account"}, required = false, description = "账号")
    private String account;

}
