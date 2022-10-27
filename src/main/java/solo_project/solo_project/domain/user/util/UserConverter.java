package solo_project.solo_project.domain.user.util;

import static solo_project.solo_project.domain.user.security.JwtExpirationEnum.REFRESH_TOKEN_EXPIRATION_TIME;

import solo_project.solo_project.domain.user.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.dto.response.LoginResponse;
import solo_project.solo_project.domain.user.dto.response.LoginResponse.LoginUser;
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

  public static LoginResponse toLoginResponse(User user, String accessToken, String refreshToken) {
    LoginResponse response = LoginResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRATION_TIME.getValue())
        .loginUser(
            LoginUser.builder()
                .id(user.getId())
                .nickname(user.getNickname().getNickname())
                .profileImage(user.getMainProfile())
                .authorities(user.getAuthorities())
                .build()
        )
        .build();
    return response;
  }

}
