package ecommerce.business.authentication.service;

import java.io.Console;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.aggregation.SetWindowFieldsOperation.SetWindowFieldsOperationBuilder.As;
import org.springframework.stereotype.Service;

import ecommerce.business.authentication.entity.User;
import ecommerce.business.authentication.entity.UserResumed;
import ecommerce.business.authentication.repository.UserRepository;
import ecommerce.utils.IException;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(User.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User singUser(User user) {
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
            return userInfo;
            // return new User(userInfo.getId(), userInfo.getName(), userInfo.getDocument(),
            // userInfo.getMobilePhone(),
            // userInfo.getHomePhone(), userInfo.getEmail());
        }

        User savedUser;
        try {
            savedUser = this.userRepository.createUser(user);
        } catch (RuntimeException ex) {
            LOG.error("Ocorreu um erro ao cadatrar novo usuário", ex);
            throw IException.ofValidation("ERROR_REGISTER_USER", "Erro ao registrar usuário.");
        }

        return savedUser;
    }
}
