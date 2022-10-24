package solo_project.solo_project.domain.user.entity;

import java.util.regex.Pattern;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@Access(value = AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Email {

  private static String EMAIL_REGEX_PATTERN = "^(.+)@(\\S+)$";
  private String email;

  public Email(String email) {
    this.email = email;
  }

  private void validationEmail(String email) {
    boolean emailMatches = Pattern.compile(EMAIL_REGEX_PATTERN)
        .matcher(email)
        .matches();
    if (!emailMatches) {
      throw new RuntimeException("입력값이 이메일 형식에 맞지 않습니다.");
    }
  }
}
