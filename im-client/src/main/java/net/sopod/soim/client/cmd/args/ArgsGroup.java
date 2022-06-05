package net.sopod.soim.client.cmd.args;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.util.List;

/**
 * ArgsCreateGroup
 *
 * @author tmy
 * @date 2022-06-05 09:32
 */
@Data
public class ArgsGroup {

//    @Parameter
//    private List<String> parameters;

    @Parameter(names = {"create"}, required = false, description = "群聊名称")
    private String groupNameCreate;

    @Parameter(names = {"search"}, required = false, description = "群聊名称")
    private String groupNameSearch;

    @Parameter(names = {"join"}, required = false, description = "群聊id")
    private Integer joinGroupId;

    @Parameter(names = {"users"}, required = false, description = "群聊id")
    private Integer usersGroupId;

}
