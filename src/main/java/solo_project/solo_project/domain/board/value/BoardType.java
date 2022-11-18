package solo_project.solo_project.domain.board.value;

public enum BoardType {
  NORMAL("일반 게시물"),
  NOTICE("공지"),
  ;

  private String description;

  BoardType(String description) {
    this.description = description;
  }

}
