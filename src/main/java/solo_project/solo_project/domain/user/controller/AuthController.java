package solo_project.solo_project.domain.user.controller;


import static solo_project.solo_project.domain.user.util.SecurityConstants.AUTHORIZATION_HEADER;
import static solo_project.solo_project.domain.user.util.SecurityConstants.EMPTY_VALUE;
import static solo_project.solo_project.domain.user.util.SecurityConstants.REFRESH_TOKEN;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.common.util.CookieUtils;
import solo_project.solo_project.domain.user.mapper.dto.response.ReissueResponse;
import solo_project.solo_project.domain.user.service.AuthService;
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
      @RequestBody @Valid LoginRequest loginRequest,
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
