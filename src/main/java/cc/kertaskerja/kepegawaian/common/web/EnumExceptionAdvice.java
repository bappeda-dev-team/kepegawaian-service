package cc.kertaskerja.kepegawaian.common.web;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import cc.kertaskerja.kepegawaian.common.domain.EnumValidationException;

@RestControllerAdvice
public class EnumExceptionAdvice {

    @ExceptionHandler(EnumValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEnumValidation(
            EnumValidationException ex) {

        return buildEnumError(
                ex.getField(),
                ex.getRejectedValue(),
                ex.getAllowedValues());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotReadable(
            HttpMessageNotReadableException ex) {

        if (ex.getCause() instanceof InvalidFormatException cause
                && cause.getTargetType().isEnum()) {

            String field = cause.getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .filter(Objects::nonNull)
                    .reduce((first, second) -> first + "." + second)
                    .orElse("unknown");

            List<String> allowedValues = getAllowedValues(cause.getTargetType());

            return buildEnumError(
                    field,
                    String.valueOf(cause.getValue()),
                    allowedValues);
        }

        return ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                "Format request body tidak valid");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        Class<?> requiredType = ex.getRequiredType();

        if (requiredType != null && requiredType.isEnum()) {
            return buildEnumError(
                    ex.getName(),
                    String.valueOf(ex.getValue()),
                    getAllowedValues(requiredType));
        }

        return ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                "Tipe parameter '%s' tidak valid".formatted(ex.getName()));
    }

    private List<String> getAllowedValues(Class<?> enumType) {
        return Arrays.stream(enumType.getEnumConstants())
                .map(value -> ((Enum<?>) value).name())
                .toList();
    }

    private ErrorResponse buildEnumError(
            String field,
            String rejectedValue,
            List<String> allowedValues) {

        String message = String.format(
                "Nilai '%s' pada field '%s' tidak valid. Pilihan yang tersedia: %s",
                rejectedValue,
                field,
                String.join(", ", allowedValues));

        return ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                message);
    }
}
