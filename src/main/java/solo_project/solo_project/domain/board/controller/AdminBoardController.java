package solo_project.solo_project.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import solo_project.solo_project.domain.board.service.AdminBoardService;

@RequestMapping("/api/v2/board")
@RestController
@RequiredArgsConstructor
public class AdminBoardController {

  private final AdminBoardService adminBoardService;

  @DeleteMapping()
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public void delete(@RequestParam(name = "boardId") Long boardId) {
    adminBoardService.deleteBoard(boardId);
  }

}
