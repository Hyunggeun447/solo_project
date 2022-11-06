package solo_project.solo_project.domain.user.mapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReissueResponse {

  private String accessToken;

}
