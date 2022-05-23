package net.sopod.soim.client.cmd.args;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.util.List;

/**
 * ArgsAddFriend
 *
 * @author tmy
 * @date 2022-05-21 19:28
 */
@Data
public class ArgsAddFriend {

    @Parameter(names = {"-u"}, required = true, description = "好友id")
    private Long friendId;

}
