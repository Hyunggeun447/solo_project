package solo_project.solo_project.domain.board.entity;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import solo_project.solo_project.common.entity.BaseEntity;


@Entity
@Table(name = "image")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE image SET is_deleted = true WHERE id = ?")
@Getter
@EqualsAndHashCode(of = "id", callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "image_url")
  private String imageUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;

  public Image(String imageUrl, Board board) {
    this.imageUrl = imageUrl;
    addBoard(board);
  }

  public void addBoard(Board board) {
    if (Objects.nonNull(this.board)) {
      this.board.getImages().remove(this);
    }
    board.addImage(this);
    this.board = board;
  }
}
