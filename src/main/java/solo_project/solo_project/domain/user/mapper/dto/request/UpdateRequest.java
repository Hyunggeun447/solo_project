package solo_project.solo_project.domain.user.mapper.dto.request;

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

  private String nickname;

  private String phoneNumber;

  private String city;

  private String detailAddress;

}
