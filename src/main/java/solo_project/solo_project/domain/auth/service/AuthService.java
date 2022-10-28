package solo_project.solo_project.domain.auth.service;


import static solo_project.solo_project.domain.user.util.UserConverter.toLoginResponse;
import static solo_project.solo_project.security.JwtExpirationEnum.ACCESS_TOKEN_EXPIRATION_TIME;
import static solo_project.solo_project.security.JwtExpirationEnum.REFRESH_TOKEN_EXPIRATION_TIME;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.common.util.CookieUtils;
import solo_project.solo_project.domain.cashe.service.CacheTokenPort;
import solo_project.solo_project.domain.user.dto.TokenInfo;
import solo_project.solo_project.domain.user.dto.request.LoginRequest;
import solo_project.solo_project.domain.user.dto.response.LoginResponse;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.repository.UserRepository;
import org.springframework.util.StringUtils;
import solo_project.solo_project.security.JwtTokenProvider;


@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {

  public static final int MAXIMAL_NUMBER_OF_WRONG_PASSWORD = 5;

  public static final String LOGOUT_KEY_PREFIX = "auth:logout#";
  public static final String LOGIN_FAILED_KEY_PREFIX = "auth:login-failed#";
  public static final String LOGIN_ACCESS_TOKEN_PREFIX = "auth:login:access-token#";
  public static final String LOGIN_REFRESH_TOKEN_PREFIX = "auth:login:refresh-token#";
  public static final String ACCESS_TOKEN = "access_token";
  public static final String REFRESH_TOKEN = "refresh_token";
  public static final String BEARER_TYPE = "Bearer ";
  private static final String EMPTY_VALUE = "";

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final CacheTokenPort cacheTokenPort;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  public LoginResponse login(LoginRequest request) {

    String email = request.getEmail();
    String password = request.getPassword();

    checkFailureCount(email);

    User user = userRepository.findByEmailEmailAddress(email)
        .orElseThrow(RuntimeException::new);
    user.checkPassword(password);

    TokenInfo tokenInfo = jwtTokenProvider.generateToken(user.getId(), email);
    saveTokenToCache(email, tokenInfo);
    return toLoginResponse(user, tokenInfo);
  }

  public void logout(HttpServletRequest request) {
    String accessToken = resolveAccessToken(request);

//    if (!jwtTokenProvider.validateToken(accessToken)) {
//      throw new RuntimeException();
//    }

//    jwtTokenProvider.validateToken(accessToken);
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
