package solo_project.solo_project.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.common.annotation.AuthUser;
import solo_project.solo_project.common.s3.UploadService;
import solo_project.solo_project.domain.user.dto.request.LoginRequest;
import solo_project.solo_project.domain.user.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.dto.request.UpdateRequest;
import solo_project.solo_project.domain.user.dto.response.LoginResponse;
import solo_project.solo_project.domain.user.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UploadService uploadService;

  @PostMapping("/signUp")
  @ResponseStatus(HttpStatus.CREATED)
  public Long signUp(@RequestBody SignUpRequest request) {
    Long userId = userService.signUp(request);
    return userId;
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse login(@RequestBody LoginRequest request) {
    LoginResponse loginResponse = userService.login(request);

    return loginResponse;
  }

  @GetMapping("/update")
  @ResponseStatus(HttpStatus.OK)
  public void update(@AuthUser Long id, UpdateRequest request) {
    userService.update(id, request);
  }

  @GetMapping("/validateEmail")
  @ResponseStatus(HttpStatus.OK)
  public boolean isDuplicatedEmail(@PathVariable(name = "email") String email) {

    boolean isDuplicatedEmail = userService.isDuplicatedEmail(email);
    return isDuplicatedEmail;
  }

  @GetMapping("/validateNickname")
  @ResponseStatus(HttpStatus.OK)
  public boolean isDuplicatedNickname(@PathVariable(name = "nickname") String nickname) {
    boolean isDuplicatedNickname = userService.isDuplicatedNickname(nickname);
    return isDuplicatedNickname;
  }
}
