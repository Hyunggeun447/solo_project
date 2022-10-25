package solo_project.solo_project.domain.user.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {

  String email = "admin@naver.com";
  String firstName = "Chun";
  String lastName = "Park";
  String nickname = "yapyap";
  String phoneNumber = "010-1234-5678";
  String city = "Seoul";
  String detailAddress = "1-1";

  @Nested
  @DisplayName("생성")
  class create {

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      User user = new User(email, firstName, lastName, nickname, phoneNumber, city, detailAddress);

      //then
      assertThat(user.getEmail().getEmailAddress()).isEqualTo(email);
      assertThat(user.getName().getFirstName()).isEqualTo(firstName);
      assertThat(user.getName().getLastName()).isEqualTo(lastName);
      assertThat(user.getNickname().getNickname()).isEqualTo(nickname);
      assertThat(user.getPhoneNumber().getNumber()).isEqualTo(phoneNumber);
      assertThat(user.getAddress().getCity()).isEqualTo(city);
      assertThat(user.getAddress().getDetailAddress()).isEqualTo(detailAddress);
      assertThat(user.getAuthorities()).contains("ROLE_USER");
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


  @Nested
  @DisplayName("수정")
  class update {

    User user;

    @BeforeEach
    void setup() {
      user = new User(email, firstName, lastName, nickname, phoneNumber, city, detailAddress);
    }

    @AfterEach
    void deleteAll() {
      user = null;
    }

    String newNickname = "paypay";

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      user.changeNickname(newNickname);

      //then
      assertThat(user.getNickname().getNickname()).isEqualTo(newNickname);
    }

    @Test
    @DisplayName("실패 - newNickname is null")
    public void F_newNickname() throws Exception {

      //when
      newNickname = null;

      //then
      assertThrows(RuntimeException.class, () -> user.changeNickname(newNickname));
    }
  }

  @Nested
  @DisplayName("조회")
  class Read {

    User user;

    @BeforeEach
    void setup() {
      user = new User(email, firstName, lastName, nickname, phoneNumber, city, detailAddress);
    }

    @AfterEach
    void deleteAll() {
      user = null;
    }

    @Nested
    @DisplayName("메인 프로필")
    class getMainProfile {

      @Test
      @DisplayName("성공 - result = null")
      public void S_null() throws Exception {

        //when
        Profile mainProfile = user.getMainProfile();

        //then
        assertThat(mainProfile).isNull();

        List<String> authorities = user.getAuthorities();
      }

      @Test
      @DisplayName("성공 - result = the most recently registered profile")
      public void S_profile() throws Exception {

        //given
        int n = 10;
        Profile profile = null;
        for (int i = 0; i < n; i++) {
          profile = new Profile("/s3/profile/url" + i, user);
        }

        //when
        Profile mainProfile = user.getMainProfile();

        //then
        assertThat(mainProfile).isEqualTo(profile);
      }
    }
  }
}