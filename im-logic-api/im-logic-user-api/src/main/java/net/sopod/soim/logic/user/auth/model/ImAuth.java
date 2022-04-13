package net.sopod.soim.logic.user.auth.model;

import lombok.Data;

/**
 * LoginParam
 *
 * @author tmy
 * @date 2022-03-31 21:06
 */
@Data
public class ImAuth {

    private String accessToken;

    private long expireMs;

}
