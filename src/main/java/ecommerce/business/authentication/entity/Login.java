package ecommerce.business.authentication.entity;

import ecommerce.utils.IException;
import ecommerce.utils.Validator;

public class Login {
    private String email;
    private String password;

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public static void validate(Login login) {
        if (Validator.isNullOrEmpty(login.getEmail())) {
            throw IException.ofValidation("USER_NAME_INVALID", "E-mail do usuário inválido.");
        }

        if (Validator.isNullOrEmpty(login.getPassword())) {
            throw IException.ofValidation("USER_DOCUMENT_INVALID", "Senha do usuário inválido");
        }
    }
}
