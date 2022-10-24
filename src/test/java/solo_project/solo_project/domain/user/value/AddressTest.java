package solo_project.solo_project.domain.user.value;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressTest {

  String city = "seoul";
  String detailAddress = "20-1";

  @Nested
  @DisplayName("생성")
  class create {

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      Address address = new Address(city, detailAddress);

      //then
      Assertions.assertThat(address.getCity()).isEqualTo(city);
      Assertions.assertThat(address.getDetailAddress()).isEqualTo(detailAddress);
    }

    @Test
    @DisplayName("실패 - city is null")
    public void F() throws Exception {

      //when
      city = null;

      //then
      assertThrows(RuntimeException.class, () -> new Address(city, detailAddress));
    }
    @Test
    @DisplayName("실패 - detailAddress is null")
    public void F_detailAddress() throws Exception {

      //when
      detailAddress = null;

      //then
      assertThrows(RuntimeException.class, () -> new Address(city, detailAddress));
    }
  }

  @Nested
  @DisplayName("수정")
  class update {

    Address address;

    @BeforeEach
    void setup() {
      address = new Address(city, detailAddress);
    }

    @AfterEach
    void deleteAll() {
      address = null;
    }

    String newCity = "busan";
    String newDetailAddress = "20-20";

    @Test
    @DisplayName("성공")
    public void S() throws Exception {

      //when
      address.changeAddress(newCity, newDetailAddress);

      //then
      Assertions.assertThat(address.getCity()).isEqualTo(newCity);
      Assertions.assertThat(address.getDetailAddress()).isEqualTo(newDetailAddress);
    }

    @Test
    @DisplayName("실패 - city is null")
    public void F() throws Exception {

      //when
      newCity = null;

      //then
      assertThrows(RuntimeException.class, () -> address.changeAddress(newCity, newDetailAddress));
    }
    @Test
    @DisplayName("실패 - detailAddress is null")
    public void F_detailAddress() throws Exception {

      //when
      newDetailAddress = null;

      //then
      assertThrows(RuntimeException.class, () -> address.changeAddress(newCity, newDetailAddress));
    }
  }
}