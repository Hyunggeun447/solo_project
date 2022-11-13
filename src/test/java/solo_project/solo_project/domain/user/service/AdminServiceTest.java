package solo_project.solo_project.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.entity.Authority;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.pojo.CustomUserDetails;
import solo_project.solo_project.domain.user.repository.AuthorityRepository;
import solo_project.solo_project.domain.user.repository.UserRepository;
import solo_project.solo_project.domain.user.value.AuthorityLevel;

@SpringBootTest
@Transactional
class AdminServiceTest {

  @Autowired
  AdminService adminService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserService userService;

  @Autowired
  CustomUserDetailsService customUserDetailsService;

  @AfterEach
  void clean() {
    userRepository.deleteAll();
  }

  String email = "email@naver.com";
  String firstName = "John";
  String lastName = "Park";
  String nickname = "JP";
  String phoneNumber = "010-1234-1234";
  String city = "Seoul";
  String detailAddress = "street 1";
  String password = "!q2w3e4r5t";

  User adminUser;
  User normalUser;
  Long targetUserId;

  @BeforeEach
  void setup() {

    targetUserId = userService.signUp(SignUpRequest.builder()
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .nickname(nickname)
        .phoneNumber(phoneNumber)
        .city(city)
        .detailAddress(detailAddress)
        .password(password)
        .build());

    Long adminUserId = userService.signUp(SignUpRequest.builder()
        .email("admin@google.com")
        .firstName("관")
        .lastName("리자")
        .nickname("관리자")
        .phoneNumber(phoneNumber)
        .city(city + UUID.randomUUID().toString().substring(0, 4))
        .detailAddress(detailAddress + UUID.randomUUID().toString().substring(0, 4))
        .password(password + UUID.randomUUID().toString().substring(0, 4))
        .build());

    adminUser = userRepository.findById(adminUserId)
        .orElseThrow(RuntimeException::new);
    Authority.addAdminAuth(adminUser);

    Long normalUserId = userService.signUp(SignUpRequest.builder()
        .email("normal@google.com")
        .firstName("일반")
        .lastName("유저")
        .nickname("일반유저")
        .phoneNumber(phoneNumber)
        .city(city + UUID.randomUUID().toString().substring(0, 4))
        .detailAddress(detailAddress + UUID.randomUUID().toString().substring(0, 4))
        .password(password + UUID.randomUUID().toString().substring(0, 4))
        .build());

    normalUser = userRepository.findById(normalUserId)
        .orElseThrow(RuntimeException::new);
  }

  @Nested
  @DisplayName("banUser test")
  class BanUserTest {

    @Test
    @DisplayName("성공: 관리자는 임의의 계정을 lock시킬 수 있음")
    public void banUserTest() throws Exception {

      //given
      CustomUserDetails adminUserDetails =
          customUserDetailsService.loadUserByUsername(adminUser.getEmail().getEmailAddress());

      //when
      adminService.banUser(targetUserId, adminUserDetails);

      //then
      User user = userRepository.findById(targetUserId)
          .orElseThrow(RuntimeException::new);

      assertThat(user.getIsNonLocked()).isFalse();
    }

    @Test
    @DisplayName("실패: 권한이 없는 유저는 임의의 계정을 lock시킬 수 없음")
    public void failBanUserForHasNotAuthTest() throws Exception {

      //when
      CustomUserDetails userNotAdmin =
          customUserDetailsService.loadUserByUsername(normalUser.getEmail().getEmailAddress());

      //then
      assertThrows(RuntimeException.class,
          () -> adminService.banUser(targetUserId, userNotAdmin));
    }

  }

  @Nested
  @DisplayName("giveAuth test")
  class GiveAuthTest {

    @Test
    @DisplayName("성공: 관리자는 임의의 유저에게 특정 권한들을 부여할 수 있음")
    public void giveAuthTest() throws Exception {

      //given
      CustomUserDetails adminUserDetails =
          customUserDetailsService.loadUserByUsername(adminUser.getEmail().getEmailAddress());

      //when
      adminService.giveAuth(targetUserId, AuthorityLevel.ADMIN, adminUserDetails);

      //then
      User user = userRepository.findById(targetUserId)
          .orElseThrow(RuntimeException::new);
      assertThat(user.getAuthorities()).contains("ROLE_ADMIN");
    }

    @Test
    @DisplayName("실패: admin 권한이 없는 유저는 임의의 유저에게 특정 권한들을 부여할 수 없음")
    public void failGiveAuthNotAdminTest() throws Exception {

      //given
      CustomUserDetails normalUserDetails =
          customUserDetailsService.loadUserByUsername(normalUser.getEmail().getEmailAddress());

      //then
      assertThrows(RuntimeException.class,
          () -> adminService.giveAuth(targetUserId, AuthorityLevel.ADMIN, normalUserDetails));
    }

  }

  @Nested
  @DisplayName("removeAuth test")
  class RemoveAuthTest {

    AuthorityRepository authorityRepository;

    @Test
    @DisplayName("성공: userId의 auth가 지워짐")
    public void removeAuthTest() throws Exception {

      //given
      CustomUserDetails adminUserDetails =
          customUserDetailsService.loadUserByUsername(adminUser.getEmail().getEmailAddress());

      //when
      adminService.removeAuth(targetUserId, AuthorityLevel.USER, adminUserDetails);

      //then
      User user = userRepository.findById(targetUserId)
          .orElseThrow(RuntimeException::new);

      assertThat(user.getAuthorities().contains("ROLE_USER")).isFalse();
    }

  }

}