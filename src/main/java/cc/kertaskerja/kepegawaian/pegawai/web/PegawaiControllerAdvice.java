package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.common.web.ErrorResponse;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiAlreadyExistsException;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiByRoleNotFoundException;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PegawaiControllerAdvice {
    @ExceptionHandler(PegawaiNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handlePegawaiNotFoundException(PegawaiNotFoundException exception) {
        return ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    @ExceptionHandler(PegawaiByRoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handlePegawaiByRoleNotFoundException(PegawaiByRoleNotFoundException exception) {
        return ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    @ExceptionHandler(PegawaiAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse handlePegawaiConflictException(PegawaiAlreadyExistsException exception) {
        return ErrorResponse.of(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }
}
