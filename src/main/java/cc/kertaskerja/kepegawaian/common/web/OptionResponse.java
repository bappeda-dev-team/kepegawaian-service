package cc.kertaskerja.kepegawaian.common.web;

import java.util.function.Function;

import cc.kertaskerja.kepegawaian.common.domain.Optionable;

public record OptionResponse(
        String label,
        String value) {

    public static OptionResponse of(Optionable option) {
        if (option == null) {
            return null;
        }
        return new OptionResponse(
                option.optionLabel(),
                option.optionValue());
    }

    // Custom value, label tetap dari Optionable
    public static <T extends Optionable> OptionResponse of(
            T option,
            Function<T, String> valueMapper) {
        if (option == null) {
            return null;
        }

        return new OptionResponse(
                option.optionLabel(),
                valueMapper.apply(option));
    }
}
