package solo_project.solo_project.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
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
import solo_project.solo_project.domain.user.mapper.dto.request.DeleteUserRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdatePasswordRequest;
import solo_project.solo_project.domain.user.mapper.dto.request.UpdateUserRequest;
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

  DecimalFormat decimalFormat = new DecimalFormat("00000000");

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
  class SignUp {

    SignUpRequest signUpRequest;

    String duplicateEmail = "isDuple@hello.com";
    String duplicateNickname = "isDuple";

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

      userService.signUp(SignUpRequest.builder()
          .email(duplicateEmail)
          .firstName(firstName)
          .lastName(lastName)
          .nickname(nickname + UUID.randomUUID().toString().substring(0, 4))
          .phoneNumber("010" + decimalFormat.format(new Random().nextInt(100000000)))
          .city(city)
          .detailAddress(detailAddress)
          .password(password)
          .build());

      userService.signUp(SignUpRequest.builder()
          .email(email + UUID.randomUUID().toString().substring(0, 4))
          .firstName(firstName)
          .lastName(lastName)
          .nickname(duplicateNickname)
          .phoneNumber("010" + decimalFormat.format(new Random().nextInt(100000000)))
          .city(city)
          .detailAddress(detailAddress)
          .password(password)
          .build());
    }

    @Test
    @DisplayName("성공: 생성요청에 맞은 User 객체 생성 및 초기값")
    public void signUpTest() throws Exception {

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
      assertThat(user.getAuthorities()).containsExactly("ROLE_USER");
      assertThat(user.getIsNonLocked()).isTrue();
      assertThat(user.getIsDeleted()).isFalse();
      assertThat(user.getMainProfile()).isNull();
    }

    @Test
    @DisplayName("실패: 중복 이메일이 있을 경우 생성이 불가능합니다.")
    public void duplicateEmail() throws Exception {

      //then
      assertThrows(RuntimeException.class,
          () -> userService.signUp(SignUpRequest.builder()
              .email(duplicateEmail)
              .firstName(firstName + UUID.randomUUID().toString().substring(0, 4))
              .lastName(lastName + UUID.randomUUID().toString().substring(0, 4))
              .nickname(nickname + UUID.randomUUID().toString().substring(0, 4))
              .phoneNumber("010" + decimalFormat.format(new Random().nextInt(100000000)))
              .city(city + UUID.randomUUID().toString().substring(0, 4))
              .detailAddress(detailAddress + UUID.randomUUID().toString().substring(0, 4))
              .password(password + UUID.randomUUID().toString().substring(0, 4))
              .build()));

    }

    @Test
    @DisplayName("실패: 중복 닉네임이 있을 경우 생성이 불가능합니다.")
    public void duplicateNickname() throws Exception {

      //then
      assertThrows(RuntimeException.class,
          () -> userService.signUp(SignUpRequest.builder()
              .email(email + UUID.randomUUID().toString().substring(0, 4))
              .firstName(firstName + UUID.randomUUID().toString().substring(0, 4))
              .lastName(lastName + UUID.randomUUID().toString().substring(0, 4))
              .nickname(duplicateNickname)
              .phoneNumber("010" + decimalFormat.format(new Random().nextInt(100000000)))
              .city(city + UUID.randomUUID().toString().substring(0, 4))
              .detailAddress(detailAddress + UUID.randomUUID().toString().substring(0, 4))
              .password(password + UUID.randomUUID().toString().substring(0, 4))
              .build()));
    }

  }


  @Nested
  @DisplayName("update test")
  class Update {

    Long userId;

    @BeforeEach
    void setup() {

      userId = userService.signUp(SignUpRequest.builder()
          .email(email)
          .firstName(firstName)
          .lastName(lastName)
          .nickname(nickname)
          .phoneNumber(phoneNumber)
          .city(city)
          .detailAddress(detailAddress)
          .password(password)
          .build());
    }

    String newNickname = nickname + UUID.randomUUID().toString().substring(0, 4);
    String newPhoneNumber = "010" + decimalFormat.format(new Random().nextInt(100000000));
    String newCity = UUID.randomUUID().toString().substring(0, 4);
    String newDetailAddress = detailAddress + UUID.randomUUID().toString().substring(0, 4);

    @Test
    @DisplayName("성공: 요청대로 entity update")
    public void updateTest() throws Exception {

      //given
      UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
          .nickname(newNickname)
          .phoneNumber(newPhoneNumber)
          .city(newCity)
          .detailAddress(newDetailAddress)
          .build();

      //when
      userService.update(userId, updateUserRequest);

      //then
      User user = userRepository.findById(userId)
          .orElseThrow(RuntimeException::new);

      assertThat(updateUserRequest).usingRecursiveComparison()
          .ignoringFields("city", "detailAddress")
          .isEqualTo(user);

      assertThat(updateUserRequest).usingRecursiveComparison()
          .ignoringFields("nickname", "phoneNumber")
          .isEqualTo(user.getAddress());
    }


    @Test
    @DisplayName("실패: 중복 닉네임이 있을 경우 update에 실패해야함")
    public void failUpdateForDuplicateNicknameTest() throws Exception {

      //given
      newNickname = nickname;

      //when
      UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
          .nickname(newNickname)
          .phoneNumber(newPhoneNumber)
          .city(newCity)
          .detailAddress(newDetailAddress)
          .build();

      //then
      assertThrows(RuntimeException.class, () -> userService.update(userId, updateUserRequest));
    }
  }

  @Nested
  @DisplayName("updatePassword test")
  class UpdatePassword {

    Long userId;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {

      userId = userService.signUp(SignUpRequest.builder()
          .email(email)
          .firstName(firstName)
          .lastName(lastName)
          .nickname(nickname)
          .phoneNumber(phoneNumber)
          .city(city)
          .detailAddress(detailAddress)
          .password(password)
          .build());
    }

    String newPassword = password + UUID.randomUUID().toString().substring(0, 4);

    @Test
    @DisplayName("성공: 기존 비밀번호 일치할 경우 새 비밀번호로 변경")
    public void updatePasswordTest() throws Exception {

      //given
      UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
          .prePassword(password)
          .newPassword(newPassword)
          .build();

      //when
      userService.updatePassword(userId, updatePasswordRequest);

      //then
      User user = userRepository.findById(userId)
          .orElseThrow(RuntimeException::new);

      assertThat(passwordEncoder.matches(newPassword, user.getPassword())).isTrue();
    }

    @Test
    @DisplayName("실패: 기존 비밀번호 불일치일 경우 에러 발생")
    public void failUpdatePasswordForWrongPrePasswordTest() throws Exception {

      //given
      password = password + UUID.randomUUID().toString().substring(0, 4);

      //when
      UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
          .prePassword(password)
          .newPassword(newPassword)
          .build();

      //then
      assertThrows(RuntimeException.class,
          () -> userService.updatePassword(userId, updatePasswordRequest));
    }

  }

  @Nested
  @DisplayName("delete test")
  class Delete {

    Long userId;

    @BeforeEach
    void setup() {

      userId = userService.signUp(SignUpRequest.builder()
          .email(email)
          .firstName(firstName)
          .lastName(lastName)
          .nickname(nickname)
          .phoneNumber(phoneNumber)
          .city(city)
          .detailAddress(detailAddress)
          .password(password)
          .build());
    }

    @Test
    @DisplayName("성공: id가 존재하고 비밀번호가 일치할 경우 삭제 성공")
    public void deleteTest() throws Exception {

      //given
      DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder()
          .password(password)
          .build();

      //when
      userService.delete(userId, deleteUserRequest);

      //then
      assertThrows(RuntimeException.class,
          () -> userRepository.findById(userId).orElseThrow(RuntimeException::new));
    }

    @Test
    @DisplayName("실패: id가 존재하나 비밀번호가 불일치할 경우 삭제 실패")
    public void failDeleteForWrongPasswordTest() throws Exception {

      //given
      password = password + UUID.randomUUID().toString().substring(0, 4);

      //when
      DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder()
          .password(password)
          .build();

      //then
      assertThrows(RuntimeException.class,
          () -> userService.delete(userId, deleteUserRequest));
    }

    @Test
    @DisplayName("실패: user id가 존재하지 않아 실패")
    public void failDeleteForWrongUserIdTest() throws Exception {

      //given
      userId = -1L;

      //when
      DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder()
          .password(password)
          .build();

      //then
      assertThrows(RuntimeException.class,
          () -> userService.delete(userId, deleteUserRequest));
    }

  }

}