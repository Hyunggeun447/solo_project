package solo_project.solo_project.domain.user.value;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import solo_project.solo_project.domain.user.entity.User;

@Table(name = "profile")
@Entity
@Getter
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE profile SET is_deleted = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "profile_url")
  private String profileUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;

  public Profile(String profileUrl, User user) {
    addUser(user);
    this.profileUrl = profileUrl;
  }

  public void addUser(User user) {
    if (Objects.nonNull(this.user)) {
      this.user.getProfiles().remove(this);
    }
    user.addProfile(this);
    this.user = user;
  }
}
