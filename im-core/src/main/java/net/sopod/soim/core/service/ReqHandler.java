package net.sopod.soim.core.service;

/**
 * ReqHandler
 *
 * @author tmy
 * @date 2022-03-28 14:33
 */
public interface ReqHandler<T> {

    Object handle(T param);

}
