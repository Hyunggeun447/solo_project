package solo_project.solo_project.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.board.entity.Board;
import solo_project.solo_project.domain.board.mapper.CreateBoardRequestMapper;
import solo_project.solo_project.domain.board.mapper.request.CreateBoardRequest;
import solo_project.solo_project.domain.board.repository.BoardRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  private final CreateBoardRequestMapper createBoardRequestMapper;

  public Long createBoard(Long userId, CreateBoardRequest createBoardRequest) {

    Board board = createBoardRequestMapper.toEntity(createBoardRequest);
    board.setUserId(userId);
    return boardRepository.save(board).getId();
  }

}
