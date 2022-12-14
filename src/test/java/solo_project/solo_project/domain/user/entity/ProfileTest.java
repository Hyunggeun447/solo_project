package solo_project.solo_project.domain.user.entity;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProfileTest {

  /**
   * User - email - firstName - lastName - nickname - phoneNumber - city - detailAddress
   */
  String email = "admin@naver.com";
  String firstName = "Chun";
  String lastName = "Park";
  String nickname = "yapyap";
  String phoneNumber = "010-1234-5678";
  String city = "Seoul";
  String detailAddress = "1-1";
  String password = "!q2w3e4r5t";
  User user = new User(email, firstName, lastName, nickname, phoneNumber, city, detailAddress, password);

  /**
   * Profile - profileUrl
   */
  String profileUrl = "/s3/.../profile1";

  @Nested
  @DisplayName("생성")
  class create {

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      String mainProfile = user.getMainProfile();
      assertThat(mainProfile).isNull();

      //when
      Profile profile = new Profile(profileUrl, user);

      //then
      assertThat(profile.getProfileUrl()).isEqualTo(profileUrl);
      assertThat(user.getProfiles().size()).isEqualTo(1);
      assertThat(user.getMainProfile()).isEqualTo(profile.getProfileUrl());
    }

    @Test
    @DisplayName("실패 - profileUrl is null")
    public void F_profileUrl() throws Exception {

      //when
      profileUrl = null;

      //then
      Assertions.assertThrows(RuntimeException.class, () -> new Profile(profileUrl, user));
    }

    @Test
    @DisplayName("실패 - user is null")
    public void F_user() throws Exception {

      //when
      user = null;

      //then
      Assertions.assertThrows(RuntimeException.class, () -> new Profile(profileUrl, user));
    }
  }

}