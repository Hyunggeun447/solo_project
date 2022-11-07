package solo_project.solo_project.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.common.annotation.AuthUserId;
import solo_project.solo_project.domain.board.mapper.request.CreateBoardRequest;
import solo_project.solo_project.domain.board.service.BoardService;

@RequestMapping("/api/v1/board")
@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_USER')")
  public void createBoard(
      @AuthUserId Long userId,
      CreateBoardRequest createBoardRequest) {

    boardService.createBoard(userId, createBoardRequest);
  }

}
