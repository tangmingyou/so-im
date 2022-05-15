package net.sopod.soim.logic.api.user.auth.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * LoginParam
 *
 * @author tmy
 * @date 2022-03-31 21:06
 */
@Data
@Accessors(chain = true)
public class ImAuth implements Serializable {

    private static final long serialVersionUID = 879498316013292363L;

    private boolean success;

    private Long uid;

    private String authToken;

    private Long expireMs;

    private String message;

}
