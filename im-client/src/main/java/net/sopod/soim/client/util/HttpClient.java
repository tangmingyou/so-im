package net.sopod.soim.client.util;

import com.google.common.net.MediaType;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONElement;
import net.sopod.soim.client.config.ClientConfig;
import net.sopod.soim.common.util.Jackson;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpClient
 *
 * @author tmy
 * @date 2022-04-25 22:06
 */
public class HttpClient {

    @SuppressWarnings("unchecked")
    public static <T> T restPost(String url, Map<?, ?> params, Class<T> resType) {
        HttpResponse<String> res = Unirest.post(url)
                .body(Jackson.json().serialize(params))
                .contentType("application/json")
                .charset(StandardCharsets.UTF_8)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .asString();
        if (String.class.equals(resType)) {
            return (T) res.getBody();
        }
        return Jackson.json().deserialize(res.getBody(), resType);
    }

    public static <T> T restGet(String url, Class<T> resType) {
        return restGet(url, Collections.emptyMap(), resType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T restGet(String url, Map<String, Object> queryString, Class<T> resType) {
        HttpResponse<String> res = Unirest.get(url)
                .queryString(queryString)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .asString();
        if (String.class.equals(resType)) {
            return (T) res.getBody();
        }
        return Jackson.json().deserialize(res.getBody(), resType);
    }

}
