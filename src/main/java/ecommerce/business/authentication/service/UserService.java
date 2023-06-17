package ecommerce.business.authentication.service;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ecommerce.business.authentication.entity.Login;
import ecommerce.business.authentication.entity.User;
import ecommerce.business.authentication.repository.UserRepository;
import ecommerce.utils.IException;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(User.class);

    private final UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        LOG.info("Iniciando cadastro de novo usuário.");

        if (user == null) {
            throw IException.ofValidation("USER_INVALID", "Usuário inválido.");
        }

        User.validate(user);

        User userInfo;
        try {
            userInfo = this.userRepository.findUserByDocument(user.getDocument());
        } catch (RuntimeException ex) {
            LOG.error("Ocorreu um erro ao buscar usuário {}", ex.getMessage());
            throw IException.ofValidation("ERROR_SEARCH_REGISTER_USER", "Erro ao buscar registro de usuário.");
        }

        if (userInfo != null) {
            throw IException.ofValidation("USER_CREATE_ERROR", "Documento já cadastrado.");
        }

        user.init();

        try {
            return this.userRepository.createUser(user);
        } catch (RuntimeException ex) {
            LOG.error("Ocorreu um erro ao cadatrar novo usuário {}", ex.getMessage());
            throw IException.ofValidation("USER_CREATE_ERROR", "Erro ao registrar usuário.");
        }
    }

    public String login(Login login) {

        Login.validate(login);

        User user = userRepository.findUserByUsername(login.getUsername());

        if (user == null || !passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw IException.ofValidation("USER_INVALID", "Usuário ou senha inválidos");
        }

        return tokenService.genarateToken(user);
    }

}
