package solo_project.solo_project.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.common.annotation.AuthUser;
import solo_project.solo_project.common.s3.UploadService;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdatePasswordRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdateRequest;
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
    return userService.signUp(request);
  }

  @PutMapping("/update")
//  @PreAuthorize("hasRole('ROLE_USER')")
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @ResponseStatus(HttpStatus.OK)
  public void update(@AuthUser Long id, UpdateRequest request) {
    userService.update(id, request);
  }

  @PutMapping("/update/password")
  @ResponseStatus(HttpStatus.OK)
  public void changePassword(@AuthUser Long id, UpdatePasswordRequest request) {
    userService.updatePassword(id, request);
  }

  @GetMapping("/validateEmail")
  @ResponseStatus(HttpStatus.OK)
  public boolean isDuplicatedEmail(@RequestParam(name = "email") String email) {
    return userService.isDuplicatedEmail(email);
  }

  @GetMapping("/validateNickname")
  @ResponseStatus(HttpStatus.OK)
  public boolean isDuplicatedNickname(@RequestParam(name = "nickname") String nickname) {
    return userService.isDuplicatedNickname(nickname);
  }
}
