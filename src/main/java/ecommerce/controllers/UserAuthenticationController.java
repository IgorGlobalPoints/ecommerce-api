package ecommerce.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.business.authentication.entity.Login;
import ecommerce.business.authentication.entity.User;
import ecommerce.business.authentication.service.UserService;
import ecommerce.utils.ApiReturn;
import ecommerce.utils.BaseController;

@RestController
@RequestMapping("user/authentication")
public class UserAuthenticationController extends BaseController {
    private final UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAuthenticationController(Executor executor, UserService userService) {
        super(executor);
        this.userService = userService;
    }

    @PostMapping("/sign")
    public CompletableFuture<ApiReturn<String>> createUser(@RequestBody User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return asyncResultOf(() -> this.userService.createUser(user));
    }

    @PostMapping("/login")
    public CompletableFuture<ApiReturn<String>> login(@RequestBody Login login) {
        return asyncResultOf(() -> this.userService.login(login));
    }

}
