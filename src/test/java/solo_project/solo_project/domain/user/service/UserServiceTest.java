package solo_project.solo_project.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.repository.UserRepository;

@SpringBootTest
@Transactional
class UserServiceTest {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  String email = "email@naver.com";
  String firstName = "John";
  String lastName = "Park";
  String nickname = "JP";
  String phoneNumber = "010-1234-1234";
  String city = "Seoul";
  String detailAddress = "street 1";
  String password = "!q2w3e4r5t";

  @AfterEach
  void clean() {
    userRepository.deleteAll();
  }

  @Nested
  @DisplayName("signUp test")
  class signUp {

    SignUpRequest signUpRequest;

    @BeforeEach
    void setup() {
      signUpRequest = SignUpRequest.builder()
          .email(email)
          .firstName(firstName)
          .lastName(lastName)
          .nickname(nickname)
          .phoneNumber(phoneNumber)
          .city(city)
          .detailAddress(detailAddress)
          .password(password)
          .build();
    }

    @Test
    @DisplayName("성공: ")
    public void signUpTest() throws Exception {

      //given

      //when
      Long userId = userService.signUp(signUpRequest);

      //then
      User user = userRepository.findById(userId)
          .orElseThrow(RuntimeException::new);

      assertThat(user.getEmail().getEmailAddress()).isEqualTo(email);
      assertThat(user.getName()).isEqualTo(firstName + " " + lastName);
      assertThat(user.getNickname()).isEqualTo(nickname);
      assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
      assertThat(user.getAddress().getCity()).isEqualTo(city);
      assertThat(user.getAddress().getDetailAddress()).isEqualTo(detailAddress);
      assertThat(passwordEncoder.matches(password, user.getPassword())).isTrue();

    }

  }


}