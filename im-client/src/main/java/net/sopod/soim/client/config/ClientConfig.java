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

    private String entryHttpHost = "http://localhost:3021";

}
