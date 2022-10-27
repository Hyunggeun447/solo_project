package solo_project.solo_project.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import solo_project.solo_project.domain.user.dto.response.LoginResponse.LoginUser;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenInfo {

  private String accessToken;

  private String refreshToken;

  private Long refreshTokenExpirationTime;

  private LoginUser loginUser;

  private String grantType;

}
