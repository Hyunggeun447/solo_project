package solo_project.solo_project.domain.user.util;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import solo_project.solo_project.domain.user.mapper.dto.TokenInfo;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.mapper.dto.response.LoginResponse;
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

  public static LoginResponse toLoginResponse(User user, TokenInfo tokenInfo) {
    LoginResponse response = LoginResponse.builder()
        .accessToken(tokenInfo.getAccessToken())
        .build();
    return response;
  }

}
