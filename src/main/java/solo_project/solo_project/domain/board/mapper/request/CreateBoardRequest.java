package solo_project.solo_project.domain.board.mapper.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import solo_project.solo_project.domain.board.value.BoardType;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBoardRequest {

  private String title;
  private String description;
  private BoardType boardType;

}
