package solo_project.solo_project.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import solo_project.solo_project.domain.board.entity.Board;
import solo_project.solo_project.domain.board.repository.BoardRepository;

@Component
@RequiredArgsConstructor
public class Checker {

  private final BoardRepository boardRepository;

  public boolean isLoginUsersBoard(Long userId, Long boardId) {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(RuntimeException::new);
    return board.getUserId().equals(userId);
  }

}
