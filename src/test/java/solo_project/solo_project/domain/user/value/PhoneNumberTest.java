package solo_project.solo_project.domain.user.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PhoneNumberTest {

  String number = "010-1234-5678";

  @Test
  @DisplayName("생성 성공")
  public void S() throws Exception {

    //when
    PhoneNumber phoneNumber = new PhoneNumber(number);

    //then
    assertThat(phoneNumber.getPhoneNumber()).isEqualTo(number);
  }

  @Test
  @DisplayName("생성 실패 - number isn't match regex - 2-4-4")
  public void F_regex244() throws Exception {

    //when
    number = "01-1234-5678";

    //then
    assertThrows(RuntimeException.class, () -> new PhoneNumber(number));
  }

  @Test
  @DisplayName("생성 실패 - number isn't match regex - 4-4-4")
  public void F_regex444() throws Exception {

    //when
    number = "0100-1234-5678";

    //then
    assertThrows(RuntimeException.class, () -> new PhoneNumber(number));
  }

  @Test
  @DisplayName("생성 실패 - number isn't match regex - 3-3-4")
  public void F_regex334() throws Exception {

    //when
    number = "010-123-5678";

    //then
    assertThrows(RuntimeException.class, () -> new PhoneNumber(number));
  }

  @Test
  @DisplayName("생성 실패 - number isn't match regex - 3-5-4")
  public void F_regex354() throws Exception {

    //when
    number = "010-12345-5678";

    //then
    assertThrows(RuntimeException.class, () -> new PhoneNumber(number));
  }

  @Test
  @DisplayName("생성 실패 - number isn't match regex - 3-4-3")
  public void F_regex343() throws Exception {

    //when
    number = "010-1234-567";

    //then
    assertThrows(RuntimeException.class, () -> new PhoneNumber(number));
  }

  @Test
  @DisplayName("생성 실패 - number isn't match regex - 3-4-5")
  public void F_regex345() throws Exception {

    //when
    number = "010-1234-56789";

    //then
    assertThrows(RuntimeException.class, () -> new PhoneNumber(number));
  }

}