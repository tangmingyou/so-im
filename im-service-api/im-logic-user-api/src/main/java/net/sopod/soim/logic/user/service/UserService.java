package net.sopod.soim.logic.user.service;


import net.sopod.soim.logic.common.model.UserInfo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * UserService
 *
 * @author tmy
 * @date 2022-03-27 22:42
 */
public interface UserService {

    /**
     * 异步接口测试
     */
    CompletableFuture<String> sayHi(String name);

    String sayHello(String name);


    List<UserInfo> onlineUserList(String keyword);


}
