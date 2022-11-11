package solo_project.solo_project.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.board.entity.Board;
import solo_project.solo_project.domain.board.repository.BoardRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminBoardService {

  private final BoardRepository boardRepository;

  public void deleteBoard(Long boardId) {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(RuntimeException::new);
    boardRepository.delete(board);
  }

}
