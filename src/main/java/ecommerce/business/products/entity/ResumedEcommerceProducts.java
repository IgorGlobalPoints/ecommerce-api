package ecommerce.business.products.entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("EcommerceProducts")
public class ResumedEcommerceProducts {

    @Id
    private UUID id;
    private boolean active;
    private String name;
    private String category;
    private Integer quantity;
    private Date createDate;

    public ResumedEcommerceProducts(UUID id, boolean active, String name,
            String category, Integer quantity, Date createDate) {
        this.id = id;
        this.active = true;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.createDate = createDate;

    }

    public UUID getId() {
        return this.id;
    }

    public boolean getActive() {
        return this.active;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
}
