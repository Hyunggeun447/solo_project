package solo_project.solo_project.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.board.entity.Board;
import solo_project.solo_project.domain.board.mapper.CreateBoardRequestMapper;
import solo_project.solo_project.domain.board.mapper.request.CreateBoardRequest;
import solo_project.solo_project.domain.board.mapper.response.BoardSummaryResponse;
import solo_project.solo_project.domain.board.repository.BoardRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminBoardService {

  private final BoardRepository boardRepository;

  private final CreateBoardRequestMapper createBoardRequestMapper;

  public Long createNotice(Long userId, CreateBoardRequest createBoardRequest) {
    Board board = createBoardRequestMapper.toEntity(createBoardRequest);
    boardRepository.save(board);
    board.setUserId(userId);
    return board.getId();
  }

  public void deleteBoard(Long boardId) {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(RuntimeException::new);
    boardRepository.delete(board);
  }

  public Page<BoardSummaryResponse> findNoticePage(Pageable pageable) {
    return boardRepository.findNoticePage(pageable);
  }

}
