package solo_project.solo_project.domain.user.mapper.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenInfo {

  private String accessToken;

  private String refreshToken;

  private long accessTokenExpirationTime;

  private Long refreshTokenExpirationTime;

}
