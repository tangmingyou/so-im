package net.sopod.soim.core.net;

import io.netty.util.AttributeKey;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AttributeKeys
 *
 * @author tmy
 * @date 2022-04-10 23:26
 */
public interface AttributeKeys {

    /** channel 写失败次数 */
    AttributeKey<AtomicInteger> WRITE_FAIL_TIMES = AttributeKey.valueOf("WRITE_FAIL_TIMES");

    /** channel 登录失败次数 */
    AttributeKey<AtomicInteger> LOGIN_FAIL_TIMES = AttributeKey.valueOf("LOGIN_FAIL_TIMES");

}
