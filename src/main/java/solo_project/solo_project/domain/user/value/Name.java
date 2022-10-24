package solo_project.solo_project.domain.user.value;

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
public class Name {

  private String FirstName;
  private String LastName;

  public Name(String firstName, String lastName) {
    Assert.notNull(firstName, "need firstName");
    Assert.notNull(lastName, "need lastName");
    FirstName = firstName;
    LastName = lastName;
  }
}
