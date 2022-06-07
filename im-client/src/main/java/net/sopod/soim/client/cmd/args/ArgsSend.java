package net.sopod.soim.client.cmd.args;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.util.List;

/**
 * ArgsSend
 *
 * @author tmy
 * @date 2022-04-28 14:03
 */
@Data
public class ArgsSend {

    @Parameter
    private List<String> parameters;

    @Parameter(names = {"-u",  "account"}, required = false, description = "接收者")
    private String account;

    @Parameter(names = {"-g", "group"}, required = false, description = "接收者")
    private Long groupId;

}
