package cc.kertaskerja.kepegawaian.web;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fieldErrors
) {
    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                null
        );
    }

    public static ErrorResponse validationFailed(Map<String, String> fieldErrors) {
        return new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "validation failed",
                fieldErrors
        );
    }
}
