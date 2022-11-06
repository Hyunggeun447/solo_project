package solo_project.solo_project.domain.user.value;

import static solo_project.solo_project.common.regex.CommonRegex.PASSWORD_REGEX_PATTERN;
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

  private String hashedPassword;

  public Password(String password) {
    Assert.notNull(password, "need password");

    validateRegex(password, PASSWORD_REGEX_PATTERN);
    this.hashedPassword = encrypt(password);
  }

  public void changePassword(String prePassword, String newPassword) {
    Assert.notNull(prePassword, "need prePassword");
    Assert.notNull(newPassword, "need newPassword");

    validateRegex(newPassword, PASSWORD_REGEX_PATTERN);
    isMatch(prePassword);

    this.hashedPassword = encrypt(newPassword);
  }

  public void isMatch(String password) {
    if (!BCrypt.checkpw(password, hashedPassword)) {
      throw new RuntimeException();
    }
  }

  private String encrypt(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
}
