package net.sopod.soim.entry.http.controller;

import net.sopod.soim.entry.http.model.ao.PwdAuthAO;
import net.sopod.soim.logic.api.user.auth.model.ImAuth;
import net.sopod.soim.logic.api.user.auth.service.UserAuthService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AuthController
 *
 * @author tmy
 * @date 2022-04-13 23:09
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @DubboReference
    private UserAuthService userAuthService;

    @PostMapping("/pwdAuth")
    public ImAuth pwdAuth(@RequestBody PwdAuthAO authAO) {
        return userAuthService.pwdAuth(authAO.getAccount(), authAO.getPassword());
    }

}
