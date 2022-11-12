package solo_project.solo_project.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.common.annotation.AuthUserId;
import solo_project.solo_project.domain.board.mapper.request.CreateBoardRequest;
import solo_project.solo_project.domain.board.mapper.response.BoardSummaryResponse;
import solo_project.solo_project.domain.board.service.AdminBoardService;

@RequestMapping("/api/v2/board")
@RestController
@RequiredArgsConstructor
public class AdminBoardController {

  private final AdminBoardService adminBoardService;


  @PostMapping("/notice")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public Long createNotice(
      @AuthUserId Long userId,
      @RequestBody CreateBoardRequest createBoardRequest
  ) {
    return adminBoardService.createNotice(userId, createBoardRequest);
  }

  @DeleteMapping()
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public void delete(@RequestParam(name = "boardId") Long boardId) {
    adminBoardService.deleteBoard(boardId);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
  public Page<BoardSummaryResponse> getNoticePage(Pageable pageable) {
    return adminBoardService.findNoticePage(pageable);
  }
}
