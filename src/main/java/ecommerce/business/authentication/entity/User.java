package ecommerce.business.authentication.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ecommerce.utils.IException;
import ecommerce.utils.Validator;

@Document("Users")
public class User implements UserDetails {

    @Id
    private UUID id;
    private String name;
    private String document;
    private String mobilePhone;
    private String homePhone;
    private String email;
    private String password;

    public User(UUID id, String name, String document, String mobilePhone, String homePhone, String email,
            String password) {
        if (id == null) {
            this.id = UUID.randomUUID();
        } else {
            this.id = id;
        }
        this.name = name;
        this.document = document;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.email = email;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDocument() {
        return this.document;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public String getHomePhone() {
        return this.homePhone;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static void validate(User user) {
        if (Validator.isNullOrEmpty(user.getName())) {
            throw IException.ofValidation("USER_NAME_INVALID", "Nome do usuário inválido.");
        }

        if (Validator.isNullOrEmpty(user.getDocument())) {
            throw IException.ofValidation("USER_DOCUMENT_INVALID", "Documento do usuário inválido");
        }

        if (Validator.isNullOrEmpty(user.getEmail())) {
            throw IException.ofValidation("EMAIL_INVALID", "Email inválido.");
        }

        if (Validator.isNullOrEmpty(user.getMobilePhone())) {
            throw IException.ofValidation("MOBILE_PHONE_INVALID", "Número de celular inválido.");
        }

        if (Validator.isNullOrEmpty(user.getHomePhone())) {
            throw IException.ofValidation("HOME_PHONE_INVALID", "Número residencial inválido.");
        }
    }
}
