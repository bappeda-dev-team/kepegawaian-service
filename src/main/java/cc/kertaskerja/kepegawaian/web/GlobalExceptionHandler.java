package cc.kertaskerja.kepegawaian.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {

        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        this::jsonFieldName,
                        FieldError::getDefaultMessage,
                        (first, second) -> first
                ));

        return ErrorResponse.validationFailed(errors);
    }

    private String jsonFieldName(FieldError error) {
        return objectMapper
                .getPropertyNamingStrategy()
                .nameForField(
                        objectMapper.getSerializationConfig(),
                        null,
                        error.getField()
                );
    }
}
