package solo_project.solo_project.domain.user.util;


import solo_project.solo_project.domain.user.dto.TokenInfo;
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

  public static LoginResponse toLoginResponse(User user, TokenInfo tokenInfo) {
    LoginResponse response = LoginResponse.builder()
        .accessToken(tokenInfo.getAccessToken())
        .refreshToken(tokenInfo.getRefreshToken())
        .refreshTokenExpirationTime(tokenInfo.getRefreshTokenExpirationTime())
        .grantType(tokenInfo.getGrantType())
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
