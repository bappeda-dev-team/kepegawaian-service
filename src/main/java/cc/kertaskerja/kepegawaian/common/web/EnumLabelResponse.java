package cc.kertaskerja.kepegawaian.common.web;

import cc.kertaskerja.kepegawaian.common.domain.LabeledEnum;

public record EnumLabelResponse(
        String label,
        String value
) {
    public static EnumLabelResponse of(LabeledEnum value) {
        if (value == null) {
            return null;
        }
        return new EnumLabelResponse(
                value.label(),
                ((Enum<?>) value).name()
        );
    }
}
