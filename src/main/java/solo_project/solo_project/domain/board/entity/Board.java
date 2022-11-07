package solo_project.solo_project.domain.board.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import solo_project.solo_project.common.entity.BaseEntity;

@Entity
@Table(name = "board")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE board SET is_deleted = true WHERE id = ?")
@Getter
@EqualsAndHashCode(of = "id", callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  private String title;

  private String description;

  private Long userId;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Image> images = new ArrayList<>();

  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;


  public void addImage(Image image) {
    this.images.add(image);
  }
}
