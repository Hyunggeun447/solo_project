package solo_project.solo_project.common.exception.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

  private String message;

  public ErrorResponse(String message) {
    this.message = message;
  }
}
