package solo_project.solo_project.domain.user.entity;

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
public class PhoneNumber {

  private String phoneNumber;

  public PhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public static PhoneNumber of(String phoneNumber) {
    return new PhoneNumber(phoneNumber);
  }

  private void validationPhone(String number) {
    if (!number.matches("^\\d{3}-\\d{4}-\\d{4}$")) {
      throw new RuntimeException("입력값이 전화번호 형식에 맞지 않습니다.");
    }
  }



}
