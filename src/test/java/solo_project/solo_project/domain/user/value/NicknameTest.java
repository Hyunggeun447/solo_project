package solo_project.solo_project.domain.user.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NicknameTest {

  String inputNickname = "yapyap";

  @Nested
  @DisplayName("생성")
  class create {

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      Nickname nickname = new Nickname(inputNickname);

      //then
      assertThat(nickname.getNickname()).isEqualTo(inputNickname);
    }

    @Test
    @DisplayName("실패 - inputNickname is null")
    public void F_inputNickname() throws Exception {

      //when
      inputNickname = null;

      //then
      assertThrows(RuntimeException.class, () -> new Nickname(inputNickname));
    }

    @Test
    @DisplayName("실패 - inputNickname length is over size")
    public void F_inputNickname2() throws Exception {

      //when
      inputNickname = "123456789";

      //then
      assertThrows(RuntimeException.class, () -> new Nickname(inputNickname));
    }

    @Test
    @DisplayName("실패 - inputNickname length is small size")
    public void F_inputNickname3() throws Exception {

      //when
      inputNickname = "1";

      //then
      assertThrows(RuntimeException.class, () -> new Nickname(inputNickname));
    }
  }

  @Nested
  @DisplayName("수정")
  class update {

    Nickname nickname;

    @BeforeEach
    void setup() {
      nickname = new Nickname(inputNickname);
    }

    @AfterEach
    void deleteAll() {
      nickname = null;
    }

    String newNickname = "paypay";

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      nickname.changeNickname(newNickname);

      //then
      assertThat(nickname.getNickname()).isEqualTo(newNickname);
    }

    @Test
    @DisplayName("실패 - inputNickname is null")
    public void F_inputNickname() throws Exception {

      //when
      newNickname = null;

      //then
      assertThrows(RuntimeException.class, () -> nickname.changeNickname(newNickname));
    }

    @Test
    @DisplayName("실패 - inputNickname length is over size")
    public void F_inputNickname2() throws Exception {

      //when
      newNickname = "123456789";

      //then
      assertThrows(RuntimeException.class, () -> nickname.changeNickname(newNickname));
    }

    @Test
    @DisplayName("실패 - inputNickname length is small size")
    public void F_inputNickname3() throws Exception {

      //when
      newNickname = "1";

      //then
      assertThrows(RuntimeException.class, () -> nickname.changeNickname(newNickname));
    }

  }

}