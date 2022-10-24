package solo_project.solo_project.domain.user.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

@Embeddable
@Getter
@Access(value = AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Address {

  private String city;
  private String detailAddress;

  public Address(String city, String detailAddress) {
    Assert.notNull(city, "need city");
    Assert.notNull(city, "need detailAddress");
    this.city = city;
    this.detailAddress = detailAddress;
  }

  public static Address of(String city, String detailAddress) {
    return new Address(city, detailAddress);
  }
}
