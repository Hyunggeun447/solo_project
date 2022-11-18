package solo_project.solo_project.domain.board.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import solo_project.solo_project.domain.board.value.BoardType;

@SpringBootTest
class BoardTest {

  String TITLE = "title";
  String DESCRIPTION = "I can write description";
  Long USER_ID = 1L;
  BoardType BOARD_TYPE = BoardType.NORMAL;

  @Nested
  @DisplayName("create Board")
  class create {

    @Test
    @DisplayName("성공: board 생성에 성공")
    public void createBoard() throws Exception {

      //when
      Board board = new Board(TITLE, DESCRIPTION, USER_ID, BOARD_TYPE);

      //then
      assertThat(board.getTitle()).isEqualTo(TITLE);
      assertThat(board.getDescription()).isEqualTo(DESCRIPTION);
      assertThat(board.getUserId()).isEqualTo(USER_ID);
      assertThat(board.getBoardType()).isEqualTo(BOARD_TYPE);
    }

    @Test
    @DisplayName("실패: title is null")
    public void failCreateBoardTitleIsNull() throws Exception {

      //given
      TITLE = null;

      //then
      assertThrows(RuntimeException.class,
          () -> new Board(TITLE, DESCRIPTION, USER_ID, BOARD_TYPE));
    }

    @Test
    @DisplayName("실패: title is empty")
    public void failCreateBoardTitleIsEmpty() throws Exception {

      //given
      TITLE = "";

      //then
      assertThrows(RuntimeException.class,
          () -> new Board(TITLE, DESCRIPTION, USER_ID, BOARD_TYPE));
    }

    @Test
    @DisplayName("실패: title is blank")
    public void failCreateBoardTitleIsBlank() throws Exception {

      //given
      TITLE = " ";

      //then
      assertThrows(RuntimeException.class,
          () -> new Board(TITLE, DESCRIPTION, USER_ID, BOARD_TYPE));
    }

    @Test
    @DisplayName("실패: description is null")
    public void failCreateBoardDescriptionIsNull() throws Exception {

      //given
      DESCRIPTION = null;

      //then
      assertThrows(RuntimeException.class,
          () -> new Board(TITLE, DESCRIPTION, USER_ID, BOARD_TYPE));
    }

    @Test
    @DisplayName("실패: description is empty")
    public void failCreateBoardDescriptionIsEmpty() throws Exception {

      //given
      DESCRIPTION = "";

      //then
      assertThrows(RuntimeException.class,
          () -> new Board(TITLE, DESCRIPTION, USER_ID, BOARD_TYPE));
    }

    @Test
    @DisplayName("성공: description is blank")
    public void createBoardDescriptionIsBlank() throws Exception {

      //given
      DESCRIPTION = " ";

      //when
      Board board = new Board(TITLE, DESCRIPTION, USER_ID, BOARD_TYPE);

      //then
      assertThat(board.getTitle()).isEqualTo(TITLE);
      assertThat(board.getDescription()).isEqualTo(DESCRIPTION);
      assertThat(board.getUserId()).isEqualTo(USER_ID);
      assertThat(board.getBoardType()).isEqualTo(BOARD_TYPE);
    }

    @Test
    @DisplayName("실패: userId is null")
    public void failCreateBoardUserIdIsNull() throws Exception {

      //given
      USER_ID = null;

      //then
      assertThrows(RuntimeException.class,
          () -> new Board(TITLE, DESCRIPTION, USER_ID, BOARD_TYPE));
    }

    @Test
    @DisplayName("실패: boardType is null")
    public void failCreateBoardBoardTypeIsNull() throws Exception {

      //given
      BOARD_TYPE = null;

      //then
      assertThrows(RuntimeException.class,
          () -> new Board(TITLE, DESCRIPTION, USER_ID, BOARD_TYPE));
    }
  }

}