package solo_project.solo_project.domain.user.mapper.dto.request;

import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

  @Pattern(regexp = "^(.+)@(\\S+)$")
  private String email;

  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
  private String password;

  public UsernamePasswordAuthenticationToken toAuthenticationToken() {
    return new UsernamePasswordAuthenticationToken(email, password);
  }

}
