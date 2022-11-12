package solo_project.solo_project.domain.user.pojo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.mapper.dto.request.SignUpRequest;
import solo_project.solo_project.domain.user.repository.UserRepository;
import solo_project.solo_project.domain.user.service.UserService;

@SpringBootTest
@Transactional
class CustomUserDetailsTest {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  String email = "email@naver.com";
  String firstName = "John";
  String lastName = "Park";
  String nickname = "JP";
  String phoneNumber = "010-1234-1234";
  String city = "Seoul";
  String detailAddress = "street 1";
  String password = "!q2w3e4r5t";

  Long userId;

  @BeforeEach
  void setup() {
    SignUpRequest signUpRequest = SignUpRequest.builder()
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .nickname(nickname)
        .phoneNumber(phoneNumber)
        .city(city)
        .detailAddress(detailAddress)
        .password(password)
        .build();

    userId = userService.signUp(signUpRequest);
  }

  @Test
  @DisplayName("CustomUserDetails 생성 성공 : 값이 동일하며 초기 권한 ROLE_USER, 각종 제약 조건 true")
  public void createCustomUserDetails() throws Exception {

    //given
    User user = userRepository.findById(userId)
        .orElseThrow(RuntimeException::new);

    //when
    CustomUserDetails customUserDetails = CustomUserDetails.of(user);

    //then
    assertThat(customUserDetails.getUsername()).isEqualTo(email);
    assertThat(customUserDetails.getPassword()).isEqualTo(user.getPassword());
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
    assertThat(customUserDetails.getAuthorities().contains(grantedAuthority)).isTrue();
    assertThat(customUserDetails.isAccountNonExpired()).isTrue();
    assertThat(customUserDetails.isEnabled()).isTrue();
    assertThat(customUserDetails.isAccountNonLocked()).isTrue();
    assertThat(customUserDetails.isCredentialsNonExpired()).isTrue();
  }


}