package solo_project.solo_project.domain.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import solo_project.solo_project.common.annotation.AuthUserId;
import solo_project.solo_project.common.s3.UploadService;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdatePasswordRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdateRequest;
import solo_project.solo_project.domain.user.mapper.dto.response.UserSelfInfoResponse;
import solo_project.solo_project.domain.user.service.AuthService;
import solo_project.solo_project.domain.user.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final AuthService authService;
  private final UploadService uploadService;

  @PostMapping("/signUp")
  @PreAuthorize("isAnonymous()")
  @ResponseStatus(HttpStatus.CREATED)
  public Long signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
    return userService.signUp(signUpRequest);
  }

  @PutMapping("/update")
//  @PreAuthorize("hasRole('ROLE_USER')")
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @ResponseStatus(HttpStatus.OK)
  public void update(
      @AuthUserId Long id,
      @RequestBody @Valid UpdateRequest updateRequest
  ) {
    userService.update(id, updateRequest);
  }

  @PutMapping("/update/password")
  @ResponseStatus(HttpStatus.OK)
  public void changePassword(
      @AuthUserId Long id,
      @RequestBody @Valid UpdatePasswordRequest updatePasswordRequest,
      HttpServletRequest request
  ) {
    userService.updatePassword(id, updatePasswordRequest);
    authService.logout(request);
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

  @DeleteMapping()
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public void delete(
      @AuthUserId Long userId
  ) {
    userService.delete(userId);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public UserSelfInfoResponse getUserSelfInfo(
      @AuthUserId Long userId
  ) {
    return userService.findUserSelfInfo(userId);
  }

  @PostMapping("/profile")
  @ResponseStatus(HttpStatus.OK)
  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  public void addProfile(
      @AuthUserId Long userId,
      @RequestPart("image") MultipartFile multipartFile
  ) {
    userService.addProfile(userId, multipartFile);
  }

}
