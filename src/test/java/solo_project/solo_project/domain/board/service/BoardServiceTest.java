package solo_project.solo_project.domain.board.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.board.entity.Board;
import solo_project.solo_project.domain.board.mapper.request.CreateBoardRequest;
import solo_project.solo_project.domain.board.repository.BoardRepository;
import solo_project.solo_project.domain.board.value.BoardType;

@SpringBootTest
@Transactional
class BoardServiceTest {

  @Autowired
  BoardService boardService;

  @Autowired
  BoardRepository boardRepository;

  String TITLE = "title";
  String DESCRIPTION = "I can write description";
  Long USER_ID = 1L;
  BoardType BOARD_TYPE = BoardType.NORMAL;

  @Nested
  @DisplayName("createBoard test")
  class CreateBoardTest {

    @Test
    @DisplayName("성공: 요청값과 동일한 board 생성")
    public void createBoardTest() throws Exception {

      //given
      CreateBoardRequest createBoardRequest = CreateBoardRequest.builder()
          .title(TITLE)
          .description(DESCRIPTION)
          .boardType(BOARD_TYPE)
          .build();

      //when
      Long boardId = boardService.createBoard(USER_ID, createBoardRequest);

      //then
      Board board = boardRepository.findById(boardId)
          .orElseThrow(RuntimeException::new);

      assertThat(createBoardRequest)
          .usingRecursiveComparison()
          .isEqualTo(board);

      assertThat(USER_ID).isEqualTo(board.getUserId());
    }

  }

}