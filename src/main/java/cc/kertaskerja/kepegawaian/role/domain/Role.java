package cc.kertaskerja.kepegawaian.role.domain;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import cc.kertaskerja.kepegawaian.common.domain.Optionable;

@Table("role")
public record Role(
        @Id Long id,

        @Column("kode_role") String kodeRole,

        @Column("nama_role") String namaRole,

        @Column("is_admin") Boolean isAdmin,

        @CreatedDate Instant createdDate,

        @LastModifiedDate Instant lastModifiedDate

) implements Optionable {

    public static Role of(
            String kodeRole,
            String namaRole,
            Boolean isAdmin) {
        return new Role(
                null,
                kodeRole,
                namaRole,
                isAdmin,
                null,
                null);
    }

    public Role update(String namaRole, Boolean isAdmin) {
        return new Role(
                id,
                kodeRole,
                namaRole,
                isAdmin,
                createdDate,
                null);
    }

    @Override
    public String optionLabel() {
        return namaRole.replace("_", " ").toUpperCase();
    }

    @Override
    public String optionValue() {
        return id.toString();
    }

    public record CreateCommand(
            String namaRole,
            Boolean isAdmin) {
    }

    public record UpdateCommand(
            String namaRole,
            Boolean isAdmin) {
    }
}
