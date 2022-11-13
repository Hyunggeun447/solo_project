package solo_project.solo_project.domain.user.service;

import static org.assertj.core.api.Assertions.*;

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
import solo_project.solo_project.domain.user.repository.UserRepository;

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

  @Nested
  @DisplayName("banUser test")
  class BanUserTest {

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
    }

    @Test
    @DisplayName("성공: 관리자는 임의의 계정을 lock시킬 수 있음")
    public void banUserTest() throws Exception {

      //given
      CustomUserDetails adminUserDetails = customUserDetailsService.loadUserByUsername(
          adminUser.getEmail().getEmailAddress());

      //when
      adminService.banUser(userId, adminUserDetails);

      //then
      User user = userRepository.findById(userId)
          .orElseThrow(RuntimeException::new);

      assertThat(user.getIsNonLocked()).isFalse();
    }

  }

}