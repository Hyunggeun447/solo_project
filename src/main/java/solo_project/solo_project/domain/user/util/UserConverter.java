package solo_project.solo_project.domain.user.util;

import solo_project.solo_project.domain.user.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.entity.User;

public class UserConverter {

  public static User toUser(SignUpRequest request) {
    return new User(
        request.getEmail(),
        request.getFirstName(),
        request.getLastName(),
        request.getNickname(),
        request.getPhoneNumber(),
        request.getCity(),
        request.getDetailAddress(),
        request.getPassword()
    );

  }

}
