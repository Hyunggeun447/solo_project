package solo_project.solo_project.domain.user.value;

import java.util.regex.Pattern;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

@Embeddable
@Getter
@Access(value = AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Nickname {

  private static final String NICKNAME_REGEX_PATTERN = "^.{2,8}$";

  private String nickname;

  public Nickname(String nickname) {
    Assert.notNull(nickname, "need nickname");

    validationNickname(nickname);
    this.nickname = nickname;
  }

  public void changeNickname(String nickname) {
    Assert.notNull(nickname, "need nickname");

    validationNickname(nickname);
    this.nickname = nickname;
  }

  private void validationNickname(String nickname) {
    boolean emailMatches = Pattern.compile(NICKNAME_REGEX_PATTERN)
        .matcher(nickname)
        .matches();
    if (!emailMatches) {
      throw new RuntimeException("입력값이 닉네임 형식에 맞지 않습니다.");
    }
  }
}
