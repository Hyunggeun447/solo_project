package solo_project.solo_project.domain.board.mapper.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.http.util.Asserts;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyBoardRequest {

  private Long boardId;

  private String title;

  private String description;

  @Builder
  public ModifyBoardRequest(Long boardId, String title, String description) {
    Asserts.notBlank(title,"can't be blank");
    this.boardId = boardId;
    this.title = title;
    this.description = description;
  }
}
