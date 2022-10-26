package solo_project.solo_project.domain.user.value;

import static solo_project.solo_project.common.util.Validation.validateRegex;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.Assert;

@Embeddable
@Getter
@Access(value = AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString

public class Password {

  private static final String PASSWORD_REGEX_PATTERN = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\\-_=+]).{8,15}$";

  private String hashedKey;

  public Password(String password) {
    Assert.notNull(password, "need password");

    validateRegex(password, PASSWORD_REGEX_PATTERN);
    this.hashedKey = encrypt(password);
  }

  public boolean isMatch(String password) {
    return BCrypt.checkpw(password, hashedKey);
  }

  private String encrypt(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
}
