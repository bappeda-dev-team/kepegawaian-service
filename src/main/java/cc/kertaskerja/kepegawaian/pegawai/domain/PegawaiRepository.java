package cc.kertaskerja.kepegawaian.pegawai.domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PegawaiRepository extends CrudRepository<Pegawai, Long> {
    Long countByStatusPegawai(PegawaiStatus statusPegawai);
    Optional<Pegawai> findByNip(String nip);
    boolean existsByNip(String nip);
    @Query("""
        SELECT *
        FROM pegawai
        WHERE keycloak_user_id IS NULL
           OR keycloak_user_id = ''
        ORDER BY id
        """)
    List<Pegawai> findWithoutKeycloakUserId();

    @Modifying
    @Query("""
        UPDATE pegawai
        SET keycloak_user_id = :keycloakUserId
        WHERE id = :pegawaiId
        """)
    void updateKeycloakUserId(
            Long pegawaiId,
            String keycloakUserId
    );
}
