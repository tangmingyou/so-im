package net.sopod.soim.logic.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * TextChat
 *
 * @author tmy
 * @date 2022-04-28 14:25
 */
@Data
@Accessors(chain = true)
public class TextChat implements Serializable {

    private static final long serialVersionUID = -3994628221046563769L;

    private Long uid;

    private Long receiverUid;

    private String receiverName;

    private String message;

    private Long time;

}
