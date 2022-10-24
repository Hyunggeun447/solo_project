package solo_project.solo_project.domain.user.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

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

  @Embedded
  @Column(name = "email")
  private Email email;

  @Embedded
  @Column(name = "name")
  private Name name;

  @Column(name = "nickname")
  private String nickname;

  @Embedded
  @Column(name = "phoneNumber")
  private PhoneNumber phoneNumber;

  @Embedded
  @Column(name = "address")
  private Address address;

  @Column(name = "profile_url")
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Profile> profiles = new ArrayList<>();

  @Column(name = "is_deleted")
  private final Boolean isDeleted = Boolean.FALSE;

  @BatchSize(size = 100)
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private final Set<Authority> authorities = new HashSet<>();

  public User(String email, String firstName, String lastName, String nickname, String phoneNumber,
      String city, String detailAddress) {
    Assert.notNull(nickname, "need nickname");
    Assert.notNull(phoneNumber, "need phoneNumber");

    this.email = new Email(email);
    this.name = new Name(firstName, lastName);
    this.nickname = nickname;
    this.phoneNumber = new PhoneNumber(phoneNumber);
    this.address = new Address(city, detailAddress);
  }


  public Profile getMainProfile() {
    Integer profileSize = this.profiles.size();
    if (profileSize.equals(0)) {
      return null;
    }
    return this.profiles.get(profileSize - 1);
  }

  public void addAuthority(Authority authority) {
    this.authorities.add(authority);
  }

  public void addProfile(Profile profile) {
    this.profiles.add(profile);
  }
}
