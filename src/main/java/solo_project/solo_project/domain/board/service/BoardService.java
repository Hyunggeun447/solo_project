package solo_project.solo_project.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.board.entity.Board;
import solo_project.solo_project.domain.board.mapper.CreateBoardRequestMapper;
import solo_project.solo_project.domain.board.mapper.ModifyBoardRequestMapper;
import solo_project.solo_project.domain.board.mapper.request.CreateBoardRequest;
import solo_project.solo_project.domain.board.mapper.request.ModifyBoardRequest;
import solo_project.solo_project.domain.board.repository.BoardRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  private final CreateBoardRequestMapper createBoardRequestMapper;
  private final ModifyBoardRequestMapper modifyBoardRequestMapper;

  public Long createBoard(Long userId, CreateBoardRequest createBoardRequest) {

    Board board = createBoardRequestMapper.toEntity(createBoardRequest);
    board.setUserId(userId);
    return boardRepository.save(board).getId();
  }

  public void modifyBoard(Long userId, ModifyBoardRequest modifyBoardRequest) {
    Board board = boardRepository.findById(modifyBoardRequest.getBoardId())
        .orElseThrow(RuntimeException::new);
    validateBoardOwner(userId, board);
    modifyBoardRequestMapper.updateFromDto(modifyBoardRequest, board);
  }

  public void deleteBoard(Long userId, Long boardId) {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(RuntimeException::new);
    validateBoardOwner(userId, board);
    boardRepository.delete(board);
  }

  private void validateBoardOwner(Long userId, Board board) {
    if (!board.getUserId().equals(userId)) {
      throw new RuntimeException();
    }
  }

}
