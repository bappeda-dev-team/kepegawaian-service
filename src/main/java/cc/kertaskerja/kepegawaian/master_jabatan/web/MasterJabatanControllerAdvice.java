package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanAlreadyExistsException;
import cc.kertaskerja.kepegawaian.common.web.ErrorResponse;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MasterJabatanControllerAdvice {
    @ExceptionHandler(MasterJabatanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse masterJabatanNotFoundHandler(MasterJabatanNotFoundException ex) {
        return ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(MasterJabatanAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse masterJabatanAlreadyExistsHandler(MasterJabatanAlreadyExistsException ex) {
        return ErrorResponse.of(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }

}
