package cc.kertaskerja.kepegawaian.common.domain;

import java.util.List;
import java.util.Arrays;

public class EnumValidationException extends RuntimeException {

    private final String field;
    private final String rejectedValue;
    private final List<String> allowedValues;

    public EnumValidationException(
            String field,
            String rejectedValue,
            Class<?> enumType) {
        super("Nilai " + field + " tidak valid");

        this.field = field;
        this.rejectedValue = rejectedValue;
        this.allowedValues = Arrays.stream(enumType.getEnumConstants())
                .map(value -> ((Enum<?>) value).name())
                .toList();
    }

    public String getField() {
        return field;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }

    public List<String> getAllowedValues() {
        return allowedValues;
    }
}
