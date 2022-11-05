package solo_project.solo_project.domain.auth.controller;


import static solo_project.solo_project.domain.user.util.SecurityConstants.AUTHORIZATION_HEADER;
import static solo_project.solo_project.domain.user.util.SecurityConstants.REFRESH_TOKEN;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.domain.auth.service.AuthService;
import solo_project.solo_project.domain.user.mapper.dto.TokenInfo;
import solo_project.solo_project.domain.user.mapper.dto.request.LoginRequest;
import solo_project.solo_project.domain.user.mapper.dto.response.LoginResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse login(
      @RequestBody LoginRequest loginRequest,
      HttpServletResponse response) {

    TokenInfo tokenInfo = authService.login(loginRequest);
    setRefreshToken(response, tokenInfo);
    response.setHeader(AUTHORIZATION_HEADER, tokenInfo.getAccessToken());
    return new LoginResponse(tokenInfo.getAccessToken());
  }

  private void setRefreshToken(HttpServletResponse response, TokenInfo tokenInfo) {
    Cookie cookie = new Cookie(REFRESH_TOKEN, tokenInfo.getRefreshToken());
    cookie.setPath("/");
    cookie.setMaxAge(Math.toIntExact(tokenInfo.getRefreshTokenExpirationTime()));
    response.addCookie(cookie);
  }

}
