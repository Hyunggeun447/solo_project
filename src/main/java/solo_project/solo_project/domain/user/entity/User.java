package solo_project.solo_project.domain.user.entity;

import static solo_project.solo_project.domain.user.entity.Authority.addUserAuth;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import solo_project.solo_project.common.entity.BaseTimeEntity;
import solo_project.solo_project.domain.user.value.Address;
import solo_project.solo_project.domain.user.value.Email;
import solo_project.solo_project.domain.user.value.Name;
import solo_project.solo_project.domain.user.value.Nickname;
import solo_project.solo_project.domain.user.value.Password;
import solo_project.solo_project.domain.user.value.Phone;

@Entity
@Table(name = "users")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

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

  @Embedded
  @Column(name = "nickname")
  private Nickname nickname;

  @Embedded
  @Column(name = "password")
  private Password password;

  @Embedded
  @Column(name = "phoneNumber")
  private Phone phoneNumber;

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
      String city, String detailAddress, String password) {

    this.email = new Email(email);
    this.name = new Name(firstName, lastName);
    this.nickname = new Nickname(nickname);
    this.phoneNumber = new Phone(phoneNumber);
    this.address = new Address(city, detailAddress);
    this.password = new Password(password);
    addUserAuth(this);
  }

  public void changeNickname(String nickname) {
    this.nickname.changeNickname(nickname);
  }

  public void changeAddress(String city, String detailAddress) {
    this.address.changeCity(city);
    this.address.changeDetailAddress(detailAddress);
  }

  public void changePhoneNumber(String number) {
    this.phoneNumber.changeNumber(number);
  }

  public String getMainProfile() {
    Integer profileSize = this.profiles.size();
    if (profileSize.equals(0)) {
      return null;
    }
    return this.profiles.get(profileSize - 1).getProfileUrl();
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
