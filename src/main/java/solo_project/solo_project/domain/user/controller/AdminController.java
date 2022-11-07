package solo_project.solo_project.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.domain.user.mapper.dto.response.UserSelfInfoResponse;
import solo_project.solo_project.domain.user.service.UserService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

  private final UserService userService;

  @PostMapping("/delete")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Secured({"ROLE_ADMIN"})
  public void deleteUser(@RequestParam Long userId) {
    userService.delete(userId);
  }

  @GetMapping("")
  @Secured({"ROLE_ADMIN"})
  public UserSelfInfoResponse getUserInfo(@RequestParam Long userId) {
    return userService.findUserSelfInfo(userId);
  }

}
