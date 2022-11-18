package solo_project.solo_project.domain.board.entity;

import static org.assertj.core.api.Assertions.*;

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
  long USER_ID = 1L;
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

  }
}