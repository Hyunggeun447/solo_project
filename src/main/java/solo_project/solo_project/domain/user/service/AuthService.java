package solo_project.solo_project.domain.user.service;


import static solo_project.solo_project.common.security.JwtExpirationEnum.ACCESS_TOKEN_EXPIRATION_TIME;
import static solo_project.solo_project.common.security.JwtExpirationEnum.REFRESH_TOKEN_EXPIRATION_TIME;
import static solo_project.solo_project.domain.user.util.SecurityConstants.IS_LOGOUT_PREFIX;
import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGIN_ACCESS_TOKEN_PREFIX;
import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGIN_FAILED_KEY_PREFIX;
import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGIN_REFRESH_TOKEN_PREFIX;
import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGOUT_KEY_PREFIX;
import static solo_project.solo_project.domain.user.util.SecurityConstants.MAXIMAL_NUMBER_OF_WRONG_PASSWORD;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.common.exception.BadRequestException;
import solo_project.solo_project.common.exception.NotFoundException;
import solo_project.solo_project.common.cache.service.CacheTokenPort;
import solo_project.solo_project.domain.user.mapper.dto.response.ReissueResponse;
import solo_project.solo_project.domain.user.mapper.dto.TokenInfo;
import solo_project.solo_project.domain.user.mapper.dto.request.LoginRequest;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.repository.UserRepository;
import org.springframework.util.StringUtils;
import solo_project.solo_project.common.security.JwtTokenProvider;


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

    User user = userRepository.findByEmailEmailAddress(email)
        .orElseThrow(() -> new NotFoundException("해당 user를 찾을 수 없습니다."));

    checkFailureCount(email);

    UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthenticationToken();
    Authentication authentication = authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    TokenInfo tokenInfo = jwtTokenProvider.getTokenResponse(user);
    saveTokenToCache(email, tokenInfo);

    return tokenInfo;
  }

  public ReissueResponse reissue(String refreshToken) {

    Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

    Long userId = jwtTokenProvider.getUserId(refreshToken);
    String email = jwtTokenProvider.getUserEmail(refreshToken);

    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);

    String redisRefreshToken = cacheTokenPort.getData(LOGIN_REFRESH_TOKEN_PREFIX + email);

    if (!jwtTokenProvider.validateToken(refreshToken) ||
        !refreshToken.equals(redisRefreshToken) ||
        jwtTokenProvider.isTokenExpired(refreshToken)) {
      throw new BadRequestException("유효하지 않은 토큰입니다.");
    }

    TokenInfo tokenInfo = jwtTokenProvider.getTokenResponse(user);

    cacheTokenPort.setDataAndExpiration(
        LOGIN_ACCESS_TOKEN_PREFIX + email,
        tokenInfo.getAccessToken(),
        ACCESS_TOKEN_EXPIRATION_TIME.getValue());

    return new ReissueResponse(tokenInfo.getAccessToken());
  }

  public void logout(HttpServletRequest request) {
    String accessToken = jwtTokenProvider.resolveToken(request);

    if (!jwtTokenProvider.validateToken(accessToken)) {
      throw new IllegalArgumentException("유효하지 않은 토큰");
    }

    cacheTokenPort.setDataAndExpiration(
        LOGOUT_KEY_PREFIX + accessToken,
        accessToken,
        ACCESS_TOKEN_EXPIRATION_TIME.getValue());

    String email = jwtTokenProvider.getUserEmail(accessToken);

    cacheTokenPort.setDataAndExpiration(
        LOGIN_REFRESH_TOKEN_PREFIX + email,
        IS_LOGOUT_PREFIX,
        REFRESH_TOKEN_EXPIRATION_TIME.getValue());
    SecurityContextHolder.clearContext();
  }

  private void saveTokenToCache(String email, TokenInfo tokenInfo) {
    cacheTokenPort.setDataAndExpiration(
        LOGIN_ACCESS_TOKEN_PREFIX + email,
        tokenInfo.getAccessToken(),
        ACCESS_TOKEN_EXPIRATION_TIME.getValue());
    cacheTokenPort.setDataAndExpiration(
        LOGIN_REFRESH_TOKEN_PREFIX + email,
        tokenInfo.getRefreshToken(),
        REFRESH_TOKEN_EXPIRATION_TIME.getValue());
  }

  private void checkFailureCount(String email) {
    String loginFailureCount = cacheTokenPort.getData(LOGIN_FAILED_KEY_PREFIX + email);

    if (StringUtils.hasText(loginFailureCount)
        && Integer.parseInt(loginFailureCount) >= MAXIMAL_NUMBER_OF_WRONG_PASSWORD) {

      throw new AccessDeniedException("비밀번호를 5회 이상 틀려 인증이 필요합니다");
    }
  }

  private Authentication authenticate(UsernamePasswordAuthenticationToken authenticationToken) {
    try {
      Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
      cacheTokenPort.deleteData(LOGIN_FAILED_KEY_PREFIX + authenticationToken.getName());
      return authentication;
    } catch (AuthenticationException e) {
      cacheTokenPort.increment(LOGIN_FAILED_KEY_PREFIX + authenticationToken.getName());
      throw e;
    }
  }

}
