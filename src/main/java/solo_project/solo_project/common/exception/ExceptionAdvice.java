package solo_project.solo_project.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import solo_project.solo_project.common.exception.dto.ErrorResponse;

@ControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Void> handleNotFoundException(NotFoundException e) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(UnAuthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnAuthorizedException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> badRequestException(BadRequestException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(Exception e) {
    return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
  }

  @ExceptionHandler(S3UploadException.class)
  public ResponseEntity<ErrorResponse> handleS3UploadException(S3UploadException e) {
    return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage()));
  }

}
