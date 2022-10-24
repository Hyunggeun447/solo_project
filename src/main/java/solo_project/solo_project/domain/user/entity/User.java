package solo_project.solo_project.domain.user.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
import solo_project.solo_project.common.entity.BaseEntity;
import solo_project.solo_project.domain.user.value.Address;
import solo_project.solo_project.domain.user.value.Email;
import solo_project.solo_project.domain.user.value.Name;
import solo_project.solo_project.domain.user.value.PhoneNumber;

@Entity
@Table(name = "users")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

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
  private List<Profile> profiles = new ArrayList<>();

  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;

  @BatchSize(size = 100)
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Authority> authorities = new HashSet<>();

  public User(String email, String firstName, String lastName, String nickname, String phoneNumber,
      String city, String detailAddress) {
    Assert.notNull(nickname, "need nickname");

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

  public List<String> getAuthorities() {
    return authorities.stream()
        .map(Authority::getRole)
        .collect(Collectors.toList());
  }

  public void addAuthority(Authority authority) {
    this.authorities.add(authority);
  }

  public void addProfile(Profile profile) {
    this.profiles.add(profile);
  }
}
