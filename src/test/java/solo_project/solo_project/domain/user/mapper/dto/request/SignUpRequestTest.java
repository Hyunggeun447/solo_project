package solo_project.solo_project.domain.user.mapper.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignUpRequestTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  String email = "email@naver.com";
  String firstName = "John";
  String lastName = "Park";
  String nickname = "JP";
  String phoneNumber = "010-1234-1234";
  String city = "Seoul";
  String detailAddress = "street 1";
  String password = "!q2w3e4r5t";

  @Test
  @DisplayName("생성 성공")
  public void signUpRequestTest() throws Exception {

    //when
    SignUpRequest signUpRequest = SignUpRequest.builder()
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .nickname(nickname)
        .city(city)
        .detailAddress(detailAddress)
        .phoneNumber(phoneNumber)
        .password(password)
        .build();

    //then
    assertThat(signUpRequest.getEmail()).isEqualTo(email);
    assertThat(signUpRequest.getFirstName()).isEqualTo(firstName);
    assertThat(signUpRequest.getLastName()).isEqualTo(lastName);
    assertThat(signUpRequest.getNickname()).isEqualTo(nickname);
    assertThat(signUpRequest.getCity()).isEqualTo(city);
    assertThat(signUpRequest.getDetailAddress()).isEqualTo(detailAddress);
    assertThat(signUpRequest.getPhoneNumber()).isEqualTo(phoneNumber);
    assertThat(signUpRequest.getPassword()).isEqualTo(password);
  }

  @Test
  @DisplayName("실패: email regex 불일치")
  public void failSignUpRequestEmailRegexTest() throws Exception {

    //given
    email = "email@";

    SignUpRequest signUpRequest = SignUpRequest.builder()
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .nickname(nickname)
        .city(city)
        .detailAddress(detailAddress)
        .phoneNumber(phoneNumber)
        .password(password)
        .build();

    //then
    Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("실패: firstName, lastName, city, detailAddress = blank")
  public void failSignUpRequestIsBlankTest() throws Exception {

    //given
    firstName = " ";
    lastName = " ";
    city = " ";
    detailAddress = " ";

    SignUpRequest signUpRequest = SignUpRequest.builder()
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .nickname(nickname)
        .city(city)
        .detailAddress(detailAddress)
        .phoneNumber(phoneNumber)
        .password(password)
        .build();

    //then
    Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
    assertThat(violations.size()).isEqualTo(4);
  }

  @Test
  @DisplayName("실패: nickname regex 불일치")
  public void failSignUpRequestNicknameRegexTest() throws Exception {

    //given
    nickname = "e";

    SignUpRequest signUpRequest = SignUpRequest.builder()
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .nickname(nickname)
        .city(city)
        .detailAddress(detailAddress)
        .phoneNumber(phoneNumber)
        .password(password)
        .build();

    //then
    Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("실패: phoneNumber regex 불일치")
  public void failSignUpRequestPhoneNumberRegexTest() throws Exception {

    //given
    phoneNumber = "02-11-111";

    SignUpRequest signUpRequest = SignUpRequest.builder()
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .nickname(nickname)
        .city(city)
        .detailAddress(detailAddress)
        .phoneNumber(phoneNumber)
        .password(password)
        .build();

    //then
    Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
    assertThat(violations.size()).isEqualTo(1);
  }

}