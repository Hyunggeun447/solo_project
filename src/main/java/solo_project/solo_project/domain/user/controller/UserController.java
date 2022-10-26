package solo_project.solo_project.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.domain.user.dto.request.SignUpRequest;

@RestController
@RequestMapping("/api/v1/users")
public interface UserController {

  ResponseEntity<Long> signUp(@RequestBody SignUpRequest request);

}
