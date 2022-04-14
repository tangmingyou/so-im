package net.sopod.soim.router.api.model;

import lombok.Data;

/**
 * CacheRes
 *
 * @author tmy
 * @date 2022-04-14 15:13
 */
@Data
public class CacheRes {

    /**
     * 是否缓存成功
     */
    private Boolean success;

    /**
     * 过期时间戳(毫秒)，0不过期
     */
    private Long expire;

    public static CacheRes success(Long expire) {
        CacheRes res = new CacheRes();
        res.setSuccess(Boolean.TRUE);
        res.setExpire(expire);
        return res;
    }

}
