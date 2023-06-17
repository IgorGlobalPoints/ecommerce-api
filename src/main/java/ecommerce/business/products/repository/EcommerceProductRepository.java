package ecommerce.business.products.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ecommerce.business.products.entity.EcommerceProduct;
import ecommerce.utils.UpdateVerify;
import ecommerce.utils.Validator;

@Repository
public class EcommerceProductRepository {
    private final MongoTemplate mongoTemplate;

    public EcommerceProductRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public EcommerceProduct saveProduct(EcommerceProduct productDetails) {
        return this.mongoTemplate.save(productDetails, "EcommerceProducts");
    }

    public boolean findProductByName(String productName) {
        var query = new Query().addCriteria(where("name").is(productName));

        return mongoTemplate.exists(query, EcommerceProduct.class);
    }

    public List<EcommerceProduct> findProducts(boolean active, String category, UUID productId, String name) {

        var query = new Query();

        if (active) {
            query.addCriteria(where("active").is(active));
        }

        if (!Validator.isNullOrEmpty(category)) {
            query.addCriteria(where("category").is(category));
        }

        if (!Validator.isNullOrEmpty(productId)) {
            query.addCriteria(where("_id").is(productId));
        }

        if (!Validator.isNullOrEmpty(name)) {
            query.addCriteria(where("name").is(name));
        }

        query.fields()
                .include("id")
                .include("name")
                .include("category")
                .include("active")
                .include("quantity")
                .include("createDate");

        return this.mongoTemplate.find(query, EcommerceProduct.class);
    }

    public EcommerceProduct findProducById(UUID productId) {
        var query = new Query().addCriteria(where("id").is(productId));

        query.fields()
                .include("id")
                .include("name")
                .include("price")
                .include("description")
                .include("imageUrl")
                .include("category")
                .include("active")
                .include("quantity");

        return this.mongoTemplate.findOne(query, EcommerceProduct.class);
    }

    public Boolean updateProduct(EcommerceProduct productDetails, UUID productId) {

        var query = new Query()
                .addCriteria(where("_id").is(productId));

        var update = new Update()
                .set("name", productDetails.getName())
                .set("description", productDetails.getDescription())
                .set("active", productDetails.getActive())
                .set("price", productDetails.getPrice())
                .set("imageUrl", productDetails.getImageUrl())
                .set("quantity", productDetails.getQuantity())
                .currentDate("updateDate");

        var result = this.mongoTemplate.updateFirst(query, update, EcommerceProduct.class);

        return UpdateVerify.wasUpdated(result);
    }
}
