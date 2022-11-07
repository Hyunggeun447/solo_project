package solo_project.solo_project.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import solo_project.solo_project.common.entity.BaseEntity;
import solo_project.solo_project.domain.user.value.AuthorityLevel;

@Table(name = "authority")
@Entity
@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority extends BaseEntity implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "role")
  private String role;

  @Override
  public String getAuthority() {
    return role;
  }

  public static void addUserAuth(User user) {
    AuthorityLevel.USER.giveAuth(user);
  }

  public static void addAdminAuth(User user) {
    AuthorityLevel.ADMIN.giveAuth(user);
  }
}
