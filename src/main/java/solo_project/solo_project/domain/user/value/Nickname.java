package solo_project.solo_project.domain.user.value;

import static solo_project.solo_project.common.util.Validation.validateRegex;

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

    validateRegex(nickname, NICKNAME_REGEX_PATTERN);
    this.nickname = nickname;
  }

  public void changeNickname(String nickname) {
    Assert.notNull(nickname, "need nickname");

    validateRegex(nickname, NICKNAME_REGEX_PATTERN);
    this.nickname = nickname;
  }

}
