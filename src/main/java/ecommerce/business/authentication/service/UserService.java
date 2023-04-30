package ecommerce.business.authentication.service;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import ecommerce.business.authentication.entity.Login;
import ecommerce.business.authentication.entity.User;
import ecommerce.business.authentication.repository.UserRepository;
import ecommerce.utils.IException;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(User.class);

    private final UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (user == null) {
            throw IException.ofValidation("USER_INVALID", "Usuário inválido.");
        }

        User.validate(user);

        User userInfo;
        try {
            userInfo = this.userRepository.findUserByDcoument(user.getDocument());
        } catch (RuntimeException ex) {
            LOG.error("Ocorreu um erro ao buscar usuário", ex);
            throw IException.ofValidation("ERROR_SEARCH_REGISTER_USER", "Erro ao buscar registro de usuário.");
        }

        if (userInfo != null) {
            throw IException.ofValidation("USER_CREATE_ERROR", "Documento já cadastrado.");
        }

        try {
            userInfo = this.userRepository.createUser(user);
        } catch (RuntimeException ex) {
            LOG.error("Ocorreu um erro ao cadatrar novo usuário", ex);
            throw IException.ofValidation("USER_CREATE_ERROR", "Erro ao registrar usuário.");
        }

        return userInfo;
    }

    public String login(Login login) {

        Login.validate(login);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                login.getEmail(), login.getPassword());

        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var user = (User) authenticate.getPrincipal();

        return tokenService.genarateToken(user);
    }
}
