package ecommerce.business.authentication.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Users")
public class UserResumed {

    @Id
    private UUID id;
    private String name;
    private String document;
    private String mobilePhone;
    private String homePhone;
    private String email;

    public UserResumed(UUID id, String name, String document, String mobilePhone, String homePhone, String email) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.email = email;
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

}
