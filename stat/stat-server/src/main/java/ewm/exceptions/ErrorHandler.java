package ewm.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidDataException.class, MethodArgumentNotValidException.class})
    public ErrorResponse handleInvalidData(final RuntimeException exception) {
        log.warn("Статус 400 - Bad Request {}", exception.getMessage());
        return new ErrorResponse("error", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerException(Exception exception) {
        log.warn("Статус 500 - Internal Error {}", exception.getMessage(), exception);
        return new ErrorResponse("error", exception.getMessage());
    }
}
