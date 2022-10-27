package solo_project.solo_project.domain.user.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

  private String accessToken;

  private String refreshToken;

  private Long refreshTokenExpirationTime;

  private LoginUser loginUser;

  private String grantType;

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class LoginUser {

    private Long id;

    private String nickname;

    private String profileImage;

    private List<String> authorities;

  }
}
