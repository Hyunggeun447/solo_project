package solo_project.solo_project.domain.user.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import solo_project.solo_project.common.s3.UploadService;
import solo_project.solo_project.domain.user.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.service.UserService;

@RequiredArgsConstructor
public class UserControllerImpl implements UserController{

  private final UserService userService;
  private final UploadService uploadService;

  @Override
  public ResponseEntity<Long> signUp(SignUpRequest request) {
    Long userId = userService.signUp(request);
    // TODO: 2022/10/26 URI 설정
    return ResponseEntity.created(URI.create("/")).body(userId);
  }
}
