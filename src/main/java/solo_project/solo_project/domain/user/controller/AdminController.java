package solo_project.solo_project.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.domain.user.mapper.dto.response.UserSelfInfoResponse;
import solo_project.solo_project.domain.user.pojo.CustomUserDetails;
import solo_project.solo_project.domain.user.service.AdminService;
import solo_project.solo_project.domain.user.service.UserService;
import solo_project.solo_project.domain.user.value.AuthorityLevel;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

  private final UserService userService;
  private final AdminService adminService;

  @PostMapping("/user/delete")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Secured({"ROLE_ADMIN"})
  public void deleteUser(@RequestParam Long userId,
      @AuthenticationPrincipal CustomUserDetails customUserDetails
  ) {
    adminService.delete(userId, customUserDetails);
  }

  @GetMapping("/user")
  @Secured({"ROLE_ADMIN"})
  @ResponseStatus(HttpStatus.OK)
  public UserSelfInfoResponse getUserInfo(@RequestParam Long userId) {
    return userService.findUserSelfInfo(userId);
  }

  @PutMapping("/ban")
  @Secured("{ROLE_ADMIN}")
  @ResponseStatus(HttpStatus.OK)
  public void banUser(
      @RequestParam Long userId,
      @AuthenticationPrincipal CustomUserDetails customUserDetails
  ) {
    adminService.banUser(userId, customUserDetails);
  }

  @PutMapping("/promote")
  @Secured("{ROLE_ADMIN}")
  @ResponseStatus(HttpStatus.OK)
  public void giveAuth(
      @RequestParam(name = "userId") Long userId,
      @RequestParam(name = "auth") AuthorityLevel auth,
      @AuthenticationPrincipal CustomUserDetails customUserDetails
  ) {
    adminService.giveAuth(userId, auth, customUserDetails);
  }

  @PutMapping("/relegation")
  @Secured("{ROLE_ADMIN}")
  @ResponseStatus(HttpStatus.OK)
  public void removeAuth(
      @RequestParam(name = "userId") Long userId,
      @RequestParam(name = "auth") AuthorityLevel auth,
      @AuthenticationPrincipal CustomUserDetails customUserDetails
  ) {
    adminService.removeAuth(userId, auth, customUserDetails);
  }
}
