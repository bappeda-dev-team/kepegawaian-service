package cc.kertaskerja.kepegawaian.common.web;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String snakeCaseFieldName = jsonFieldName(error);
            String errorMessage = error.getDefaultMessage();

            // because damn spring
            errors.put(snakeCaseFieldName, errorMessage);
        });

        return ErrorResponse.validationFailed(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex
    ) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife
                && ife.getTargetType().isEnum()) {

            String fieldName = ife.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse("field");

            String allowedValues = Arrays.stream(ife.getTargetType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body(
                    ErrorResponse.validationFailed(
                            Map.of(
                                    fieldName,
                                    "Nilai yang diperbolehkan: " + allowedValues
                            )
                    )
            );
        }

        return ResponseEntity.badRequest().body(
                ErrorResponse.of(
                        HttpStatus.BAD_REQUEST,
                        "Request body tidak valid"
                )
        );
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
