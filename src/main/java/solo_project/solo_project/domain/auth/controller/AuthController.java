package solo_project.solo_project.domain.auth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.domain.auth.service.AuthService;
import solo_project.solo_project.domain.user.mapper.dto.request.LoginRequest;
import solo_project.solo_project.domain.user.mapper.dto.response.LoginResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse login(@RequestBody LoginRequest request) {
    return authService.login(request);
  }

}
