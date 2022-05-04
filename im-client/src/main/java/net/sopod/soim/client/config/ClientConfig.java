package net.sopod.soim.client.config;

import com.google.inject.Singleton;
import lombok.Data;

/**
 * ClientConfig
 *
 * @author tmy
 * @date 2022-04-25 22:33
 */
@Singleton
@Data
public class ClientConfig {

    private String loginUrl = "http://localhost:3021/auth/pwdAuth";

    private String host = "127.0.0.1";

    private Integer port = 8087;

}
