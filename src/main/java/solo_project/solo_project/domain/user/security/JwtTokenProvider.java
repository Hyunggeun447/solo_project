package solo_project.solo_project.domain.user.security;

import static solo_project.solo_project.domain.user.security.JwtExpirationEnum.ACCESS_TOKEN_EXPIRATION_TIME;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import solo_project.solo_project.domain.user.pojo.CustomUserDetails;

@Component
@PropertySource("classpath:application.yaml")
public class JwtTokenProvider {

  @Value("${jwt.secretKey}")
  private String secretKey;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String generateAccessToken(Long id, String email) {
    return generateToken(id, email, ACCESS_TOKEN_EXPIRATION_TIME.getValue());
  }

  public Long getUserId(String token) {
    Object id = extractClaims(token).get("id");
    return Long.valueOf(String.valueOf(id));
  }

  public String getUserEmail(String token) {
    String email = (String) extractClaims(token).get("email");
    return email;
  }

  public Authentication getAuthentication(CustomUserDetails userDetails) {
    return new UsernamePasswordAuthenticationToken(userDetails, "",
        userDetails.getAuthorities());
  }

  public String resolveToken(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    if (StringUtils.hasText(authorization)) {
      return authorization;
    }
    return null;
  }

  public boolean validationToken(String token, CustomUserDetails userDetails) {
    Long userId = getUserId(token);
    String userEmail = getUserEmail(token);
    return userDetails.validate(userId, userEmail) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    Date expiration = extractClaims(token).getExpiration();
    return expiration.before(new Date());
  }

  private Claims extractClaims(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();
  }

  private String generateToken(Long id, String email, Long expireTime) {
    Claims claims = Jwts.claims();
    claims.put("id", id);
    claims.put("email", email);
    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + expireTime))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

}
