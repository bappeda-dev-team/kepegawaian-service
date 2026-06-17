package cc.kertaskerja.kepegawaian.opd.web;

import cc.kertaskerja.kepegawaian.opd.domain.OpdAlreadyExistsException;
import cc.kertaskerja.kepegawaian.opd.domain.OpdNotFoundException;
import cc.kertaskerja.kepegawaian.web.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class OpdControllerAdvice {
    @ExceptionHandler(OpdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse opdNotFoundHandler(OpdNotFoundException ex) {
        return ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(OpdAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse opdAlreadyExistsHandler(OpdAlreadyExistsException ex) {
        return ErrorResponse.of(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ErrorResponse.validationFailed(errors);
    }
}
