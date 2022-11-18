package solo_project.solo_project.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.common.annotation.AuthUserId;
import solo_project.solo_project.domain.board.mapper.request.CreateBoardRequest;
import solo_project.solo_project.domain.board.mapper.request.ModifyBoardRequest;
import solo_project.solo_project.domain.board.mapper.response.BoardDetailsResponse;
import solo_project.solo_project.domain.board.mapper.response.BoardSummaryResponse;
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

  @DeleteMapping()
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  public void deleteBoard(
      @AuthUserId Long userId,
      @RequestParam Long boardId
  ) {
    boardService.deleteBoard(userId, boardId);
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("isAnonymous()")
  public BoardDetailsResponse getBoard(
      @RequestParam Long boardId) {
    return boardService.findBoard(boardId);
  }

  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("isAnonymous()")
  public Slice<BoardSummaryResponse> getBoardList(
      @PageableDefault(page = 0, size = 15) Pageable pageable) {
    return boardService.findBoardList(pageable);
  }

}
