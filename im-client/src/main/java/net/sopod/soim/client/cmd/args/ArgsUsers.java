package net.sopod.soim.client.cmd.args;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.util.List;

/**
 * ArgsUsers
 *
 * @author tmy
 * @date 2022-04-28 12:50
 */
@Data
public class ArgsUsers {

    @Parameter
    private List<String> parameters;

    @Parameter(names = {"-k",  "-keyword"}, required = false, description = "用户查询关键字")
    private String keyword;

}
