package solo_project.solo_project.domain.user.mapper.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private String nickname;

  private String phoneNumber;

  private String city;

  private String detailAddress;

  public SignUpRequest(String email, String password, String firstName, String lastName,
      String nickname, String phoneNumber, String city, String detailAddress) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
    this.city = city;
    this.detailAddress = detailAddress;
  }
}
