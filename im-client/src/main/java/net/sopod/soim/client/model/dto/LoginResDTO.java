package net.sopod.soim.client.model.dto;

import lombok.Data;

/**
 * LoginResDTO
 *
 * @author tmy
 * @date 2022-04-25 22:36
 */
@Data
public class LoginResDTO {

    private Boolean success;

    private Long uid;

    private String authToken;

    private Long expireMs;

    private String message;

}
