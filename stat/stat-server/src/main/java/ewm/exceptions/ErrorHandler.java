package ewm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleNotValidData(final MethodArgumentNotValidException e) {

        String errorMessage = "Запрос содержит невалидные данные: поле "
                + e.getFieldError().getField()
                + " "
                + e.getFieldError().getDefaultMessage();

        return new ErrorResponse("error", errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataException.class)
    public ErrorResponse handleInvalidData(final RuntimeException e) {
        return new ErrorResponse("error", e.getMessage());
    }
}
