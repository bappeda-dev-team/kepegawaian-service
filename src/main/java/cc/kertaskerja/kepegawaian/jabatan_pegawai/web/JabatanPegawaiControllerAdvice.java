package cc.kertaskerja.kepegawaian.jabatan_pegawai.web;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiAlreadyExistsException;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiNotFoundException;
import cc.kertaskerja.kepegawaian.common.web.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JabatanPegawaiControllerAdvice {
    @ExceptionHandler(JabatanPegawaiNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse jabatanPegawaiNotFoundHandler(JabatanPegawaiNotFoundException ex) {
        return ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(JabatanPegawaiAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse jabatanPegawaiAlreadyExistsHandler(JabatanPegawaiAlreadyExistsException ex) {
        return ErrorResponse.of(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }
}
