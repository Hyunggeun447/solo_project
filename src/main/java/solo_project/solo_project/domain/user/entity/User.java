package solo_project.solo_project.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "users")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "email")
  private String email;

  @Column(name = "name")
  private String name;

  @Column(name = "nickname")
  private String nickname;

  @Embedded
  @Column(name = "phoneNumber")
  private PhoneNumber phoneNumber;

  @Embedded
  @Column(name = "address")
  private Address address;


  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;

}
