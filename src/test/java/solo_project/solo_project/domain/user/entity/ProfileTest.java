package solo_project.solo_project.domain.user.entity;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProfileTest {


  @Nested
  @DisplayName("생성")
  class create {

    /**
     * User
     * - email
     * - firstName
     * - lastName
     * - nickname
     * - phoneNumber
     * - city
     * - detailAddress
     */
    String email = "admin@naver.com";
    String firstName = "Chun";
    String lastName = "Park";
    String nickname = "yapyap";
    String phoneNumber = "010-1234-5678";
    String city = "Seoul";
    String detailAddress = "1-1";
    User user = new User(email, firstName, lastName, nickname, phoneNumber, city, detailAddress);

    /**
     * Profile
     * - profileUrl
     */
    String profileUrl = "/s3/.../profile1";

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      Profile mainProfile = user.getMainProfile();
      assertThat(mainProfile).isNull();

      //when
      Profile profile = new Profile(profileUrl, user);

      //then
      assertThat(profile.getProfileUrl()).isEqualTo(profileUrl);
      assertThat(user.getProfiles().size()).isEqualTo(1);
      assertThat(user.getMainProfile()).isEqualTo(profile);
    }
  }

}