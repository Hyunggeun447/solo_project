package solo_project.solo_project.domain.user.value;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressTest {

  @Nested
  @DisplayName("생성")
  class create {

    String seoul = "seoul";
    String detailAddress = "20-1";
    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      Address address = new Address(seoul, detailAddress);

      //then
      Assertions.assertThat(address.getCity()).isEqualTo(seoul);
      Assertions.assertThat(address.getDetailAddress()).isEqualTo(detailAddress);
    }

    @Test
    @DisplayName("실패 - city is null")
    public void F() throws Exception {

      //when
      seoul = null;

      //then
      assertThrows(RuntimeException.class, () -> new Address(seoul, detailAddress));
    }
    @Test
    @DisplayName("실패 - detailAddress is null")
    public void F_detailAddress() throws Exception {

      //when
      detailAddress = null;

      //then
      assertThrows(RuntimeException.class, () -> new Address(seoul, detailAddress));
    }
  }
}