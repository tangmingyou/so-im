package net.sopod.soim.logic.common.model.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * UserMessage
 *
 * @author tmy
 * @date 2022-06-08 16:53
 */
@Data
@Accessors(chain = true)
public class UserMessage implements Serializable {

    private static final long serialVersionUID = -572323953445204089L;

    private Long senderUid;

    private Long receiverUid;

    private String receiverName;

    private String message;

    private Long time;

}
