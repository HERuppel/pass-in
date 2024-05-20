package rocketseat.com.passin.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import rocketseat.com.passin.domain.user.User;

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;
  
  public String generateToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      String token = JWT.create()
              .withIssuer("pass-in")
              .withSubject(user.getEmail())
              .withClaim("id", user.getId())
              .withExpiresAt(generateExpirationDate())
              .sign(algorithm);

      return token;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error while generating token!", exception);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      return JWT.require(algorithm)
              .withIssuer("pass-in")
              .build()
              .verify(token)
              .getSubject();
    } catch (JWTCreationException exception) {
      return "";
    }
  }

  public Integer extractUserIdFromToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    JWTVerifier verifier = JWT.require(algorithm).build();
    var tokenInfos = verifier.verify(token);

    return tokenInfos.getClaim("id").asInt(); 
  }

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
