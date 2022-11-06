package solo_project.solo_project.domain.user.util;


import solo_project.solo_project.domain.user.mapper.dto.TokenInfo;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.mapper.dto.response.LoginResponse;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.mapper.dto.response.UserSelfInfoResponse;

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

  public static UserSelfInfoResponse toUserSelfInfo(User user) {
    return UserSelfInfoResponse.builder()
        .email(user.getEmail().getEmailAddress())
        .name(user.getName())
        .nickname(user.getNickname())
        .phone(user.getPhoneNumber())
        .address1(user.getAddress().getCity())
        .address2(user.getAddress().getDetailAddress())
        .profileUrl(user.getMainProfile())
        .build();
  }

}
