package solo_project.solo_project.domain.user.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {

  @Nested
  @DisplayName("생성")
  class create {

    String email = "admin@naver.com";
    String firstName = "Chun";
    String lastName = "Park";
    String nickname = "yapyap";
    String phoneNumber = "010-1234-5678";
    String city = "Seoul";
    String detailAddress = "1-1";

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      User user = new User(email, firstName, lastName, nickname, phoneNumber, city, detailAddress);

      //then
      assertThat(user.getEmail().getEmailAddress()).isEqualTo(email);
      assertThat(user.getName().getFirstName()).isEqualTo(firstName);
      assertThat(user.getName().getLastName()).isEqualTo(lastName);
      assertThat(user.getNickname()).isEqualTo(nickname);
      assertThat(user.getPhoneNumber().getNumber()).isEqualTo(phoneNumber);
      assertThat(user.getAddress().getCity()).isEqualTo(city);
      assertThat(user.getAddress().getDetailAddress()).isEqualTo(detailAddress);
    }

    @Test
    @DisplayName("실패 - nickname is null")
    public void F_nickname_null() throws Exception {

      //when
      nickname = null;

      //then
      assertThrows(RuntimeException.class,
          () -> new User(email, firstName, lastName, nickname, phoneNumber, city, detailAddress));
    }
  }
}