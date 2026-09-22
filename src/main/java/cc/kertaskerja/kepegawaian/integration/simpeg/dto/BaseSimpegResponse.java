package cc.kertaskerja.kepegawaian.integration.simpeg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BaseSimpegResponse<T>(
        @JsonProperty("Title")
        String title,

        @JsonProperty("Status")
        String status,

        @JsonProperty("Data")
        List<T> data
) {
}
