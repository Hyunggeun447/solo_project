package solo_project.solo_project.domain.user.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.common.s3.UploadService;
import solo_project.solo_project.domain.user.dto.request.LoginRequest;
import solo_project.solo_project.domain.user.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.dto.response.LoginResponse;
import solo_project.solo_project.domain.user.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UploadService uploadService;

  @PostMapping("/signUp")
  public ResponseEntity<Long> signUp(@RequestBody SignUpRequest request) {
    Long userId = userService.signUp(request);
    // TODO: 2022/10/26 URI 설정
    return ResponseEntity.created(URI.create("/")).body(userId);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    LoginResponse loginResponse = userService.login(request);

    return ResponseEntity.ok(loginResponse);
  }

  @GetMapping("/validate")
  public ResponseEntity<Boolean> isDuplicatedEmail(@PathVariable(name = "email") String email) {
    boolean isDuplicatedEmail = userService.isDuplicatedEmail(email);
    return ResponseEntity.ok(isDuplicatedEmail);
  }

  @GetMapping("/validate")
  public ResponseEntity<Boolean> isDuplicatedNickname(@PathVariable(name = "nickname") String nickname) {
    boolean isDuplicatedNickname = userService.isDuplicatedNickname(nickname);
    return ResponseEntity.ok(isDuplicatedNickname);
  }
}
