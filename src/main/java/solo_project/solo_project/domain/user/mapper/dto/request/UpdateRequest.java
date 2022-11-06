package solo_project.solo_project.domain.user.mapper.dto.request;

import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateRequest {

  @Pattern(regexp = "^.{2,8}$")
  private String nickname;

  @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$")
  private String phoneNumber;

  private String city;

  private String detailAddress;

}
