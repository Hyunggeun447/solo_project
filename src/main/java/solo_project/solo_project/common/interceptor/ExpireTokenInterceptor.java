package solo_project.solo_project.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import solo_project.solo_project.common.exception.BadRequestException;
import solo_project.solo_project.common.security.JwtTokenProvider;

@Component
@RequiredArgsConstructor
public class ExpireTokenInterceptor implements HandlerInterceptor {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String token = jwtTokenProvider.resolveToken(request);
    if (!(authentication instanceof AnonymousAuthenticationToken)
        && jwtTokenProvider.isTokenExpired(token)) {

      throw new BadRequestException("유효하지 않은 토큰입니다.");
    }

    return HandlerInterceptor.super.preHandle(request, response, handler);
  }

}
