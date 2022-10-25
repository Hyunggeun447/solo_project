package solo_project.solo_project.domain.user.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import solo_project.solo_project.domain.user.pojo.CustomUserDetails;
import solo_project.solo_project.domain.user.service.CustomUserDetailService;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailService customUserDetailService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    try {
      String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

      if (Objects.nonNull(token)) {
        String userEmail = jwtTokenProvider.getUserEmail(token);

        if (Objects.nonNull(userEmail)) {
          CustomUserDetails userDetails = customUserDetailService.loadUserByUsername(userEmail);

          validateAccessToken(token, userDetails);
          Authentication authentication = jwtTokenProvider.getAuthentication(userDetails);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    } catch (SignatureException | MalformedJwtException e) {
      log.info("토큰이 변조되었습니다.");
    } catch (ExpiredJwtException e) {
      log.info("토큰이 만료되었습니다.");
    }
    chain.doFilter(request, response);
  }

  private void validateAccessToken(String token, CustomUserDetails userDetails) {
    if (!jwtTokenProvider.validationToken(token, userDetails)) {
      throw new RuntimeException();
    }
  }
}
