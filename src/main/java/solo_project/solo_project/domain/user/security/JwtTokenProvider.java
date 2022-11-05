package solo_project.solo_project.domain.user.security;

import static solo_project.solo_project.domain.user.security.JwtExpirationEnum.ACCESS_TOKEN_EXPIRATION_TIME;
import static solo_project.solo_project.domain.user.security.JwtExpirationEnum.REFRESH_TOKEN_EXPIRATION_TIME;
import static solo_project.solo_project.domain.user.util.SecurityConstants.AUTHORIZATION_HEADER;
import static solo_project.solo_project.domain.user.util.SecurityConstants.BEARER_TYPE;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.mapper.dto.TokenInfo;
import solo_project.solo_project.domain.user.pojo.CustomUserDetails;
import solo_project.solo_project.domain.user.service.CustomUserDetailsService;

@Slf4j
@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
public class JwtTokenProvider {

  private final CustomUserDetailsService customUserDetailsService;

  @Value("${jwt.secretKey}")
  private String secretKey;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public TokenInfo getTokenResponse(User user) {
    String accessToken = generateToken(user, ACCESS_TOKEN_EXPIRATION_TIME.getValue());
    String refreshToken = generateToken(user, REFRESH_TOKEN_EXPIRATION_TIME.getValue());
    return TokenInfo.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .accessTokenExpirationTime(ACCESS_TOKEN_EXPIRATION_TIME.getValue())
        .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRATION_TIME.getValue())
        .build();
  }

  public Long getUserId(String token) {
    return (Long) extractClaims(token).get("id");
  }

  public String getUserEmail(String token) {
    return (String) extractClaims(token).get("email");
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = extractClaims(accessToken);

    CustomUserDetails customUserDetails =
        customUserDetailsService.loadUserByUsername(claims.getSubject());
    return new UsernamePasswordAuthenticationToken(
        customUserDetails,
        customUserDetails.getPassword(),
        customUserDetails.getAuthorities());
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      extractClaims(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT Token", e);
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT Token", e);
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT Token", e);
    } catch (IllegalArgumentException e) {
      log.info("JWT claims string is empty.", e);
    }

    return false;
  }

  public Long getExpiration(String token) {
    Date expiration = extractClaims(token).getExpiration();
    Long now = new Date().getTime();
    return (expiration.getTime() - now);
  }

  public boolean isTokenExpired(String token) {
    Date expiration = extractClaims(token).getExpiration();
    return expiration.before(new Date());
  }

  private Claims extractClaims(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  private String generateToken(User user, Long expireTime) {
    Date now = new Date();

    return Jwts.builder()
        .setSubject(user.getEmail().getEmailAddress())
        .claim("userId", user.getId())
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + expireTime))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

}
