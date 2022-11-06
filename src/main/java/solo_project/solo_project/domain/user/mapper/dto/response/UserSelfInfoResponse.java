package solo_project.solo_project.domain.user.mapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSelfInfoResponse {

  private String email;

  private String name;

  private String nickname;

  private String phone;

  private String address1;

  private String address2;

  private String profileUrl;

}
