package net.sopod.soim.logic.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AuthConfig
 *
 * @author tmy
 * @date 2022-04-20 22:29
 */
@Component
@ConfigurationProperties(prefix = "auth")
@Data
public class AuthConfig {

    /** 登录token过期时间(秒) */
    private Integer authTokenExpire = 3 * 60;

}
