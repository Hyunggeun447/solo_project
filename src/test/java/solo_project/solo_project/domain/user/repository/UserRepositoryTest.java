package solo_project.solo_project.domain.user.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.entity.User;

@SpringBootTest
@Transactional
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  String email = "admin@naver.com";
  String firstName = "Chun";
  String lastName = "Park";
  String nickname = "yapyap";
  String phoneNumber = "010-1234-5678";
  String city = "Seoul";
  String detailAddress = "1-1";
  User user = new User(email, firstName, lastName, nickname, phoneNumber, city, detailAddress);

  @Test
  @DisplayName("s : existsByNicknameNickname")
  public void S() throws Exception {

    Boolean beforeResult = userRepository.existsByNicknameNickname(nickname);
    assertThat(beforeResult).isFalse();

    userRepository.save(user);

    Boolean result = userRepository.existsByNicknameNickname(nickname);

    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("s : existsByEmailEmailAddress")
  public void S_existsByEmailEmailAddress() throws Exception {

    Boolean before = userRepository.existsByEmailEmailAddress(email);
    assertThat(before).isFalse();

    userRepository.save(user);

    Boolean result = userRepository.existsByEmailEmailAddress(email);

    assertThat(result).isTrue();
  }

}