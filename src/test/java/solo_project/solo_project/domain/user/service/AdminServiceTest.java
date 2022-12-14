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
        .firstName("???")
        .lastName("??????")
        .nickname("?????????")
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
        .firstName("??????")
        .lastName("??????")
        .nickname("????????????")
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
    @DisplayName("??????: ???????????? ????????? ????????? lock?????? ??? ??????")
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
    @DisplayName("??????: ????????? ?????? ????????? ????????? ????????? lock?????? ??? ??????")
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
    @DisplayName("??????: ???????????? ????????? ???????????? ?????? ???????????? ????????? ??? ??????")
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
    @DisplayName("??????: admin ????????? ?????? ????????? ????????? ???????????? ?????? ???????????? ????????? ??? ??????")
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
    @DisplayName("??????: userId??? auth??? ?????????")
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

    @Test
    @DisplayName("??????: ???????????? admin??? ?????? ?????? ?????? ??????")
    public void failRemoveAuthTest() throws Exception {

      //given
      CustomUserDetails normalUserDetails =
          customUserDetailsService.loadUserByUsername(normalUser.getEmail().getEmailAddress());

      //then
      assertThrows(RuntimeException.class,
          () -> adminService.removeAuth(targetUserId, AuthorityLevel.USER, normalUserDetails));
    }

    // TODO: 2022/11/13 ????????? ?????????????????????? ????????? ???????????? ????????????, ?????????  ????????? ????????? ???????????? ????????? ????????? ??????
    @Test
    @DisplayName("??????: target user??? ??????????????? ????????? ??????????????? ?????????")
    public void failRemoveAuthForNotExistAuthTest() throws Exception {

      //given
      CustomUserDetails adminUserDetails =
          customUserDetailsService.loadUserByUsername(adminUser.getEmail().getEmailAddress());

      //when
      adminService.removeAuth(targetUserId, AuthorityLevel.ADMIN, adminUserDetails);

      //then
      User user = userRepository.findById(targetUserId)
          .orElseThrow(RuntimeException::new);
      assertThat(user.getAuthorities().contains("ROLE_ADMIN")).isFalse();
    }

  }

}