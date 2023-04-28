package ecommerce.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.business.authentication.entity.User;
import ecommerce.business.authentication.service.UserService;
import ecommerce.utils.ApiReturn;
import ecommerce.utils.BaseController;

@RestController
@RequestMapping("user/authentication")
public class UserAuthenticationController extends BaseController {
    private final UserService userService;

    public UserAuthenticationController(Executor executor, UserService userService) {
        super(executor);
        this.userService = userService;
    }

    @PostMapping("sign")
    public CompletableFuture<ApiReturn<User>> signUser(@RequestBody User user) {
        return asyncResultOf(() -> this.userService.singUser(user));
    }
}
