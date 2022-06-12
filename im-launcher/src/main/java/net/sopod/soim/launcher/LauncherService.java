package net.sopod.soim.launcher;

import org.springframework.core.Ordered;

/**
 * LauncherService
 *
 * @author tmy
 * @date 2022-06-10 21:33
 */
public interface LauncherService extends Ordered {

    void launcher();

}
