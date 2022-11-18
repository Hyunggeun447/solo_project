package solo_project.solo_project.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import solo_project.solo_project.domain.board.mapper.response.BoardDetailsResponse;
import solo_project.solo_project.domain.board.mapper.response.BoardSummaryResponse;

public interface BoardSearchRepository {

  BoardDetailsResponse findBoardDetails(Long boardId);

  Slice<BoardSummaryResponse> findBoardList(Pageable pageable);

  Page<BoardSummaryResponse> findNoticePage(Pageable pageable);

}
