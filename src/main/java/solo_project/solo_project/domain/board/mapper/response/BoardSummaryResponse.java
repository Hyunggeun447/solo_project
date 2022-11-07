package solo_project.solo_project.domain.board.mapper.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardSummaryResponse {

  private String title;

  private String writer;

  private LocalDateTime createdAt;

}
