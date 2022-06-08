package net.sopod.soim.logic.common.model.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * GroupMessage
 *
 * @author tmy
 * @date 2022-06-05 11:10
 */
@Data
@Accessors(chain = true)
public class GroupMessage implements Serializable {

    private static final long serialVersionUID = 237146829521854337L;

    private Long sender;

    private Long gid;

    private String message;

    private Long time;

}
