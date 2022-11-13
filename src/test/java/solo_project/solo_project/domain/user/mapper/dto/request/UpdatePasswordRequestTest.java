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
class UpdatePasswordRequestTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  String password = "!q2w3e4r5t";
  String newPassword = "plokij123!";

  @Test
  @DisplayName("생성 성공")
  public void createUpdatePasswordRequestTest() throws Exception {

    //when
    UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
        .prePassword(password)
        .newPassword(newPassword)
        .build();

    //then
    assertThat(updatePasswordRequest.getPrePassword()).isEqualTo(password);
    assertThat(updatePasswordRequest.getNewPassword()).isEqualTo(newPassword);
  }


  @Test
  @DisplayName("실패: prePassword regex 오류")
  public void prePasswordRegexError() throws Exception {

    //given
    password = "ajkak";

    //when
    UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
        .prePassword(password)
        .newPassword(newPassword)
        .build();

    //then
    Set<ConstraintViolation<UpdatePasswordRequest>> violations = validator.validate(updatePasswordRequest);
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("실패: newPassword regex 오류")
  public void newPasswordRegexError() throws Exception {

    //given
    newPassword = "ajkak";

    //when
    UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
        .prePassword(password)
        .newPassword(newPassword)
        .build();

    //then
    Set<ConstraintViolation<UpdatePasswordRequest>> violations = validator.validate(updatePasswordRequest);
    assertThat(violations.size()).isEqualTo(1);

  }

}