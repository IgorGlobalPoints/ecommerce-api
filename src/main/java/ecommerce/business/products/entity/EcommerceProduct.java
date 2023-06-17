package ecommerce.business.products.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ecommerce.utils.Validator;

@Document("EcommerceProducts")
public class EcommerceProduct {

  @Id
  private UUID id;
  private boolean active;
  private String name;
  private String description;
  private BigDecimal price;
  private String category;
  private String imageUrl;
  private Integer quantity;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

  public void init() {
    if (Validator.isNullOrEmpty(id)) {
      id = UUID.randomUUID();
      createDate = LocalDateTime.now(ZoneOffset.UTC);
      updateDate = LocalDateTime.now(ZoneOffset.UTC);
    }
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

  public LocalDateTime getCreateDate() {
    return this.createDate;
  }

  public LocalDateTime getUpdateDate() {
    return this.updateDate;
  }
}
