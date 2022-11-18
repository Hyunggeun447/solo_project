package solo_project.solo_project.domain.user.value;

import static solo_project.solo_project.common.regex.CommonRegex.PHONE_NUMBER_REGEX_PATTERN;

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
public class Phone {

  private String number;

  public Phone(String number) {
    Assert.notNull(number, "need phoneNumber");
    validationPhone(number);
    this.number = number;
  }

  public void changeNumber(String number) {
    Assert.notNull(number, "need phoneNumber");
    validationPhone(number);
    this.number = number;
  }

  private void validationPhone(String number) {
    boolean emailMatches = Pattern.compile(PHONE_NUMBER_REGEX_PATTERN)
        .matcher(number)
        .matches();
    if (!emailMatches) {
      throw new RuntimeException("입력값이 전화번호 형식에 맞지 않습니다.");
    }
  }

}
