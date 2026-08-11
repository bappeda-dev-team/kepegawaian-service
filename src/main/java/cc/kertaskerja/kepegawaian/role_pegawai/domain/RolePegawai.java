package cc.kertaskerja.kepegawaian.role_pegawai.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("role_pegawai")
public record RolePegawai(
    @Id
    Long id,
    Long pegawaiId,
    Long roleId,

    @CreatedDate
    Instant createdDate,

    @LastModifiedDate
    Instant lastModifiedDate
) {
    public static RolePegawai create(
            Long roleId,
            Long pegawaiId
    ) {
        return new RolePegawai(
            null,
            pegawaiId,
            roleId,
            null,
            null
        );
    }
}
