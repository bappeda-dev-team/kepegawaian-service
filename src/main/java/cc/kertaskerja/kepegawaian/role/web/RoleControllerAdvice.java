package cc.kertaskerja.kepegawaian.role.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cc.kertaskerja.kepegawaian.common.web.ErrorResponse;
import cc.kertaskerja.kepegawaian.role.domain.RoleAlreadyExistsException;
import cc.kertaskerja.kepegawaian.role.domain.RoleNotFoundException;

@RestControllerAdvice
public class RoleControllerAdvice {
    @ExceptionHandler(RoleAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse roleAlreadyExistsHandler(RoleAlreadyExistsException ex) {
        return ErrorResponse.of(
                HttpStatus.CONFLICT,
                ex.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse roleNotFoundHandler(RoleNotFoundException ex) {
        return ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage());
    }
}
