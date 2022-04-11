package net.sopod.soim.logic.user.service;

import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.CompletableFuture;

/**
 * UserServiceImpl
 *
 * @author tmy
 * @date 2022-04-04 10:03
 */
@DubboService
public class UserServiceImpl implements UserService {

    @Override
    public CompletableFuture<String> sayHi(String name) {
        return CompletableFuture.completedFuture("hi," + name + "!");
    }

    @Override
    public String sayHello(String name) {
        return "hello," + name + "!";
    }

}
