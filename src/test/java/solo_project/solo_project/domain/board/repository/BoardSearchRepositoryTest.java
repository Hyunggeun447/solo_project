package solo_project.solo_project.domain.board.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import solo_project.solo_project.domain.board.entity.Board;
import solo_project.solo_project.domain.board.mapper.response.BoardDetailsResponse;
import solo_project.solo_project.domain.user.entity.User;
import solo_project.solo_project.domain.user.repository.UserRepository;

@SpringBootTest
@Transactional
class BoardSearchRepositoryTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  BoardRepository boardRepository;


  @Test
  public void findBoardDetails() throws Exception {

    //given
    User user = User.builder()
        .email("bbb@naver.com")
        .password("1q2w3e4r!")
        .firstName("hong")
        .lastName("aprk")
        .nickname("마마마")
        .phoneNumber("010-1234-1234")
        .city("서")
        .detailAddress("울")
        .build();

    Long userId = userRepository.save(user).getId();

    Board board = new Board("title", " des", userId);

    Long boardId = boardRepository.save(board).getId();

    //when
    BoardDetailsResponse boardDetails = boardRepository.findBoardDetails(boardId);

    //then
    assertThat(boardDetails.getTitle()).isEqualTo(board.getTitle());
    assertThat(boardDetails.getDescription()).isEqualTo(board.getDescription());
    assertThat(boardDetails.getWriter()).isEqualTo(user.getNickname());

  }

}