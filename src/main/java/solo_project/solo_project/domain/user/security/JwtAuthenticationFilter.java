package solo_project.solo_project.domain.user.security;

import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGOUT_KEY_PREFIX;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;
  private final RedisTemplate redisTemplate;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    String accessToken = jwtTokenProvider.resolveToken((HttpServletRequest) request);

    if (Objects.nonNull(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
      String isLogout = (String) redisTemplate.opsForValue().get(LOGOUT_KEY_PREFIX + accessToken);

      if (ObjectUtils.isEmpty(isLogout)) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    chain.doFilter(request, response);
  }

}
