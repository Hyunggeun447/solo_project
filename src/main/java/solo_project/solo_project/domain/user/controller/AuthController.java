package solo_project.solo_project.domain.auth.controller;


import static solo_project.solo_project.domain.user.util.SecurityConstants.AUTHORIZATION_HEADER;

import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.common.util.CookieUtils;
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
    CookieUtils.setRefreshToken(response, tokenInfo);
    response.setHeader(AUTHORIZATION_HEADER, tokenInfo.getAccessToken());
    return new LoginResponse(tokenInfo.getAccessToken());
  }

  @GetMapping("/reissue")
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public ReissueResponse reissue(HttpServletRequest request) {
    String refreshToken = CookieUtils.getCookie(request, REFRESH_TOKEN)
        .map(cookie -> cookie.getValue())
        .orElseGet(() -> EMPTY_VALUE);
    return authService.reissue(refreshToken);
  }

  @PostMapping("/logout")
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(HttpServletRequest request) {
    authService.logout(request);
  }

}