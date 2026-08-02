package cc.kertaskerja.kepegawaian.role.domain;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("role")
public record Role(
        @Id Long id,

        @Column("kode_role") String kodeRole,

        @Column("nama_role") String namaRole,

        @CreatedDate Instant createdDate,

        @LastModifiedDate Instant lastModifiedDate

) {
    public static Role of(
            String kodeRole,
            String namaRole) {
        return new Role(
                null,
                kodeRole,
                namaRole,
                null,
                null);
    }

    public Role update(
            String kodeRole,
            String namaRole) {
        return new Role(
                id,
                kodeRole,
                namaRole,
                createdDate,
                null);
    }
}
