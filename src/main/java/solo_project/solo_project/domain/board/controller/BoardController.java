package solo_project.solo_project.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.common.annotation.AuthUserId;
import solo_project.solo_project.domain.board.mapper.request.CreateBoardRequest;
import solo_project.solo_project.domain.board.mapper.request.ModifyBoardRequest;
import solo_project.solo_project.domain.board.service.BoardService;

@RequestMapping("/api/v1/board")
@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  public void createBoard(
      @AuthUserId Long userId,
      @RequestBody CreateBoardRequest createBoardRequest) {

    boardService.createBoard(userId, createBoardRequest);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
//  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN') and @checker.isLoginUsersBoard(#userId, #modifyBoardRequest.boardId)")
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  public void modifyBoard(
      @AuthUserId Long userId,
      @RequestBody ModifyBoardRequest modifyBoardRequest
  ) {
//    boardService.modifyBoard(modifyBoardRequest);
    boardService.modifyBoard(userId, modifyBoardRequest);
  }



}