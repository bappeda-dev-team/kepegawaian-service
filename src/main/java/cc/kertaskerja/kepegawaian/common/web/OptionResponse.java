package cc.kertaskerja.kepegawaian.common.web;

import cc.kertaskerja.kepegawaian.common.domain.Optionable;

public record OptionResponse(
        String label,
        String value
) {

    public static OptionResponse of(Optionable option) {
        if (option == null) {
            return null;
        }
        return new OptionResponse(
                option.optionLabel(),
                option.optionValue()
        );
    }
}
