package cc.kertaskerja.kepegawaian.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WebResponse<T>(
        int code,
        String status,
        String message,
        T data
) {
    public static <T> WebResponse<T> success(T data) {
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Success",
                data);
    }

    public static <T> WebResponse<T> success(String message, T data) {
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                message,
                data);
    }

    public static <T> WebResponse<T> created(String message, T data) {
        return new WebResponse<>(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase(),
                message,
                data);
    }

    public static WebResponse<Void> deleted(String message) {
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                message,
                null
        );
    }

    public static WebResponse<Void> badRequest(String message) {
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                message,
                null
        );
    }
}
