package ecommerce.business.authentication.repository;

import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import ecommerce.business.authentication.entity.User;
import ecommerce.business.authentication.entity.UserResumed;

@Repository
public class UserRepository {
    private final MongoTemplate mongoTemplate;

    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User createUser(User user) {
        return this.mongoTemplate.save(user, "Users");
    }

    public User findUserByDcoument(String document) {
        var query = new Query().addCriteria(where("document").is(document));

        return mongoTemplate.findOne(query, User.class);

    }

}
