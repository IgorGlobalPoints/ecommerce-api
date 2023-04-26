package ecommerce.business.products.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("EcommerceProducts")
public class ResumedEcommerceProduct {

    @Id
    private UUID id;
    private boolean active;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageUrl;
    private Integer quantity;

    public ResumedEcommerceProduct(UUID id, boolean active, String name, String description, BigDecimal price,
            String category, String imageUrl, Integer quantity) {
        this.id = id;
        this.active = true;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
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

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getCategory() {
        return this.category;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Integer getQuantity() {
        return this.quantity;
    }
}
