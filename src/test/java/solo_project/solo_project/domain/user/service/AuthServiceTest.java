package solo_project.solo_project.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static solo_project.solo_project.common.security.JwtExpirationEnum.ACCESS_TOKEN_EXPIRATION_TIME;
import static solo_project.solo_project.common.security.JwtExpirationEnum.REFRESH_TOKEN_EXPIRATION_TIME;
import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGIN_ACCESS_TOKEN_PREFIX;
import static solo_project.solo_project.domain.user.util.SecurityConstants.LOGIN_REFRESH_TOKEN_PREFIX;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.common.cache.service.CacheTokenPort;
import solo_project.solo_project.common.security.JwtTokenProvider;
import solo_project.solo_project.config.RedisTestContainers;
import solo_project.solo_project.domain.user.mapper.dto.TokenInfo;
import solo_project.solo_project.domain.user.mapper.dto.request.LoginRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.repository.UserRepository;

@SpringBootTest
@Transactional
class AuthServiceTest extends RedisTestContainers {

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  AuthService authService;

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  CacheTokenPort cacheTokenPort;

  String email = "email@naver.com";
  String firstName = "John";
  String lastName = "Park";
  String nickname = "JP";
  String phoneNumber = "010-1234-1234";
  String city = "Seoul";
  String detailAddress = "street 1";
  String password = "!q2w3e4r5t";

  Long userId;

  @BeforeEach
  void setup() {

    SignUpRequest signUpRequest = SignUpRequest.builder()
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .nickname(nickname)
        .phoneNumber(phoneNumber)
        .city(city)
        .detailAddress(detailAddress)
        .password(password)
        .build();

    userId = userService.signUp(signUpRequest);

  }

  @AfterEach
  void clear() {
    userRepository.deleteAll();
  }


  @Nested
  @DisplayName("LoginTest")
  class LoginTest {

    @Test
    @DisplayName("성공: 아이디와 비밀번호가 일치하며 계정이 있으면 토큰들과 그 만료시간들을 반환"
        + "redis에 토큰들을 저장")
    public void LoginTest() throws Exception {

      //given
      LoginRequest loginRequest = LoginRequest.builder()
          .email(email)
          .password(password)
          .build();

      //when
      TokenInfo tokenInfo = authService.login(loginRequest);

      String accessToken = tokenInfo.getAccessToken();
      String refreshToken = tokenInfo.getRefreshToken();

      Long accessTokenUserId = jwtTokenProvider.getUserId(accessToken);
      Long refreshTokenUserId = jwtTokenProvider.getUserId(refreshToken);

      String redisAccessToken = cacheTokenPort
          .getData(LOGIN_ACCESS_TOKEN_PREFIX + loginRequest.getEmail());
      String redisRefreshToken = cacheTokenPort
          .getData(LOGIN_REFRESH_TOKEN_PREFIX + loginRequest.getEmail());

      //then
      assertThat(accessTokenUserId).isEqualTo(userId);
      assertThat(refreshTokenUserId).isEqualTo(userId);
      assertThat(tokenInfo.getAccessTokenExpirationTime())
          .isEqualTo(ACCESS_TOKEN_EXPIRATION_TIME.getValue());
      assertThat(tokenInfo.getRefreshTokenExpirationTime())
          .isEqualTo(REFRESH_TOKEN_EXPIRATION_TIME.getValue());

      assertThat(redisAccessToken).isEqualTo(accessToken);
      assertThat(redisRefreshToken).isEqualTo(refreshToken);

    }

    @Test
    @DisplayName("실패: 틀린 비밀번호를 입력하면 로그인 실패")
    public void FailLoginTest() throws Exception {

      //given
      String wrongPassword = "4321";
      LoginRequest loginRequest = LoginRequest.builder()
          .email(email)
          .password(wrongPassword)
          .build();

      assertThrows(RuntimeException.class,
          () -> authService.login(loginRequest));
    }

  }

}