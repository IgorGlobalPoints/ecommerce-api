package ecommerce.business.authentication.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import ecommerce.business.authentication.entity.User;

@Service
public class TokenService {
    public String genarateToken(User user) {
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(30);
        Date expiryDate = Date.from(expiryTime.atZone(ZoneId.systemDefault()).toInstant());

        return JWT.create()
                .withIssuer("e-commerce-api")
                .withSubject(user.getUsername())
                .withClaim("document", user.getDocument())
                .withExpiresAt(expiryDate).sign(Algorithm.HMAC256("SECRETA"));
    }
}
