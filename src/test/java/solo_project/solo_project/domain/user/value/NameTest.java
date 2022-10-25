package solo_project.solo_project.domain.user.value;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NameTest {

  @Nested
  @DisplayName("생성")
  class create {

    String lastName = "Lee";
    String firstName = "Chun";

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      Name name = new Name(firstName, lastName);

      //then
      Assertions.assertThat(name.getFirstName()).isEqualTo(firstName);
      Assertions.assertThat(name.getLastName()).isEqualTo(lastName);
    }

    @Test
    @DisplayName("실패 - firstName is null")
    public void F_firstName() throws Exception {

      //when
      firstName = null;

      //then
      assertThrows(RuntimeException.class, () -> new Name(firstName, lastName));
    }

    @Test
    @DisplayName("실패 - lastName is null")
    public void F_lastName() throws Exception {

      //when
      lastName = null;

      //then
      assertThrows(RuntimeException.class, () -> new Name(firstName, lastName));
    }
  }

}