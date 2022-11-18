package solo_project.solo_project.domain.user.value;

import static solo_project.solo_project.common.regex.CommonRegex.EMAIL_REGEX_PATTERN;
import static solo_project.solo_project.common.util.Validation.*;

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
public class Email {

  private String emailAddress;

  public Email(String email) {
    Assert.notNull(email, "need email");
    validateRegex(email, EMAIL_REGEX_PATTERN);
    this.emailAddress = email;
  }

}
