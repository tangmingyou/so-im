package net.sopod.soim.router.cache.annotation;

import java.lang.annotation.*;

/**
 * BiSyncIgnore
 * 忽略方法同步
 *
 * @author tmy
 * @date 2022-05-04 17:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SyncIgnore {

}
