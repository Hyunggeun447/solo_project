package solo_project.solo_project.domain.board.repository;

import solo_project.solo_project.domain.board.mapper.response.BoardDetailsResponse;

public interface BoardSearchRepository {

  BoardDetailsResponse findBoardDetails(Long boardId);

}
