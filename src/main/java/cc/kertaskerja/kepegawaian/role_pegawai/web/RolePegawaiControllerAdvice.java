package cc.kertaskerja.kepegawaian.role_pegawai.web;

import cc.kertaskerja.kepegawaian.common.web.ErrorResponse;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.RolePegawaiAlreadyExists;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.RolePegawaiNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RolePegawaiControllerAdvice {
    @ExceptionHandler(RolePegawaiNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleNotFound(RolePegawaiNotFoundException exception) {
        return ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    @ExceptionHandler(RolePegawaiAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse handleConflict(RolePegawaiAlreadyExists exception) {
        return ErrorResponse.of(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }
}
