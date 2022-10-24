package solo_project.solo_project.domain.user.value;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailTest {

  @Nested
  @DisplayName("생성")
  class create {

    String emailString = "email@naver.com";

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      Email email = new Email(emailString);

      //then
      assertThat(email.getEmailAddress()).isEqualTo(emailString);
    }

    @Test
    @DisplayName("실패 - emailString is null ")
    public void F_emailString() throws Exception {

      //given
      String emailString = null;

      //then
      assertThrows(RuntimeException.class, () -> new Email(emailString));
    }

    @Test
    @DisplayName("실패 - emailString isn't match regex - not exist @")
    public void F_emailString_regex() throws Exception {

      //given
      String emailString = "email";

      //then
      assertThrows(RuntimeException.class, () -> new Email(emailString));
    }

    @Test
    @DisplayName("실패 - emailString isn't match regex - not exist @ after string")
    public void F_emailString_regex2() throws Exception {

      //given
      String emailString = "email@";

      //then
      assertThrows(RuntimeException.class, () -> new Email(emailString));
    }
  }

}