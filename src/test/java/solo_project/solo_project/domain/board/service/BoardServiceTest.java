package solo_project.solo_project.domain.board.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.board.entity.Board;
import solo_project.solo_project.domain.board.mapper.request.CreateBoardRequest;
import solo_project.solo_project.domain.board.mapper.request.ModifyBoardRequest;
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

  @AfterEach
  void clear() {
    boardRepository.deleteAll();
  }

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

  @Nested
  @DisplayName("modifyBoard test")
  class ModifyBoardTest {

    Long boardId;

    String NEW_TITLE = "수정할 제목";
    String NEW_DESCRIPTION = "수정할 내용이이이이이이";

    @BeforeEach
    void setup() {

      //given
      CreateBoardRequest createBoardRequest = CreateBoardRequest.builder()
          .title(TITLE)
          .description(DESCRIPTION)
          .boardType(BOARD_TYPE)
          .build();

      //when
      boardId = boardService.createBoard(USER_ID, createBoardRequest);
    }

    @Test
    @DisplayName("성공: board 수정 성공")
    public void modifyBoardTest() throws Exception {

      //given
      ModifyBoardRequest modifyBoardRequest = ModifyBoardRequest.builder()
          .boardId(boardId)
          .title(NEW_TITLE)
          .description(NEW_DESCRIPTION)
          .build();

      //when
      boardService.modifyBoard(USER_ID, modifyBoardRequest);

      //then
      Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

      assertThat(modifyBoardRequest)
          .usingRecursiveComparison()
          .ignoringFields("boardId")
          .isEqualTo(board);
    }

    @Test
    @DisplayName("실패: boardId is null")
    public void failModifyBoardForWrongBoardIdTest() throws Exception {

      //given
      boardId = null;

      ModifyBoardRequest modifyBoardRequest = ModifyBoardRequest.builder()
          .boardId(boardId)
          .title(NEW_TITLE)
          .description(NEW_DESCRIPTION)
          .build();

      //then
      assertThrows(RuntimeException.class,
          () -> boardService.modifyBoard(USER_ID, modifyBoardRequest));
    }

    @Test
    @DisplayName("실패: boardId is wrong")
    public void failModifyBoardForWrongBoardIdTest2() throws Exception {

      //given
      boardId = -1L;

      ModifyBoardRequest modifyBoardRequest = ModifyBoardRequest.builder()
          .boardId(boardId)
          .title(NEW_TITLE)
          .description(NEW_DESCRIPTION)
          .build();

      //then
      assertThrows(RuntimeException.class,
          () -> boardService.modifyBoard(USER_ID, modifyBoardRequest));
    }
  }

}