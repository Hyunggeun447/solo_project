package solo_project.solo_project.domain.user.mapper.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

  @Pattern(regexp = "^(.+)@(\\S+)$")
  private String email;

  @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\\-_=+]).{8,15}$")
  private String password;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @Pattern(regexp = "^.{2,8}$")
  private String nickname;

  @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$")
  private String phoneNumber;

  @NotBlank
  private String city;

  @NotBlank
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
