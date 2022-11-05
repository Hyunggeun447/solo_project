package solo_project.solo_project.domain.auth.service;


import static solo_project.solo_project.domain.user.security.JwtExpirationEnum.ACCESS_TOKEN_EXPIRATION_TIME;
import static solo_project.solo_project.domain.user.security.JwtExpirationEnum.REFRESH_TOKEN_EXPIRATION_TIME;
import static solo_project.solo_project.domain.user.util.SecurityConstants.ACCESS_TOKEN;
import static solo_project.solo_project.domain.user.util.SecurityConstants.BEARER_TYPE;
import static solo_project.solo_project.domain.user.util.SecurityConstants.EMPTY_VALUE;
import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGIN_ACCESS_TOKEN_PREFIX;
import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGIN_FAILED_KEY_PREFIX;
import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGIN_REFRESH_TOKEN_PREFIX;
import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGOUT_KEY_PREFIX;
import static solo_project.solo_project.domain.user.util.SecurityConstants.MAXIMAL_NUMBER_OF_WRONG_PASSWORD;
import static solo_project.solo_project.domain.user.util.UserConverter.toLoginResponse;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.common.exception.NotFoundException;
import solo_project.solo_project.common.util.CookieUtils;
import solo_project.solo_project.domain.cashe.service.CacheTokenPort;
import solo_project.solo_project.domain.user.mapper.dto.TokenInfo;
import solo_project.solo_project.domain.user.mapper.dto.request.LoginRequest;
import solo_project.solo_project.domain.user.mapper.dto.response.LoginResponse;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.repository.UserRepository;
import org.springframework.util.StringUtils;
import solo_project.solo_project.domain.user.security.JwtTokenProvider;


@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final CacheTokenPort cacheTokenPort;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  public TokenInfo login(LoginRequest loginRequest) {

    String email = loginRequest.getEmail();

    // TODO: 2022/11/05
    checkFailureCount(email);

    User user = userRepository.findByEmailEmailAddress(email)
        .orElseThrow(() -> new NotFoundException("해당 user를 찾을 수 없습니다."));

    UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthenticationToken();
    authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    TokenInfo tokenInfo = jwtTokenProvider.getTokenResponse(user);
    saveTokenToCache(email, tokenInfo);

    return tokenInfo;
  }

  public void logout(HttpServletRequest request) {
    String accessToken = resolveAccessToken(request);

    if (!jwtTokenProvider.validateToken(accessToken)) {
      throw new IllegalArgumentException("유효하지 않은 토큰");
    }

    cacheTokenPort.setDataAndExpiration(LOGOUT_KEY_PREFIX + accessToken, accessToken, ACCESS_TOKEN_EXPIRATION_TIME.getValue());
    SecurityContextHolder.clearContext();
  }

  private String resolveAccessToken(HttpServletRequest request) {
    return CookieUtils.getCookie(request, ACCESS_TOKEN)
        .filter(cookie -> cookie.getValue().startsWith(BEARER_TYPE))
        .map(cookie -> cookie.getValue().substring(BEARER_TYPE.length()))
        .orElseGet(() -> EMPTY_VALUE);
  }

  private void saveTokenToCache(String email, TokenInfo tokenInfo) {
    cacheTokenPort.setDataAndExpiration(LOGIN_ACCESS_TOKEN_PREFIX + email,
        tokenInfo.getAccessToken(), ACCESS_TOKEN_EXPIRATION_TIME.getValue());
    cacheTokenPort.setDataAndExpiration(LOGIN_REFRESH_TOKEN_PREFIX + email,
        tokenInfo.getRefreshToken(), REFRESH_TOKEN_EXPIRATION_TIME.getValue());
  }

  private void checkFailureCount(String email) {
    String loginFailureCount = cacheTokenPort.getData(LOGIN_FAILED_KEY_PREFIX + email);

    if (StringUtils.hasText(loginFailureCount) && Integer.parseInt(loginFailureCount) >= MAXIMAL_NUMBER_OF_WRONG_PASSWORD) {
      throw new AccessDeniedException("비밀번호를 5회 이상 틀려 인증이 필요합니다");
    }
  }


}
