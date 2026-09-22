package cc.kertaskerja.kepegawaian.mapping_opd.domain;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("mapping_opd")
public record MappingOpd(
        @Id
        Long id,

        @NotBlank
        @Column("sumber")
        SumberMapping sumber,

        @NotBlank
        @Column("kode_sumber")
        // kode sinkron
        String kodeSumber,

        @NotBlank
        @Column("kode_master")
        // kode dsini
        String kodeMaster,

        @CreatedDate
        @Column("created_at")
        Instant createdAt,

        @LastModifiedDate
        @Column("updated_at")
        Instant updatedAt
) {
}
