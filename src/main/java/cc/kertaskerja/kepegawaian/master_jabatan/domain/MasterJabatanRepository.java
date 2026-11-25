package cc.kertaskerja.kepegawaian.master_jabatan.domain;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MasterJabatanRepository extends CrudRepository<MasterJabatan, Long>, MasterJabatanRepositoryCustom {
    List<MasterJabatan> findAll();

    List<MasterJabatan> findAllByOpdIdAndStatusJabatanOrderByNamaJabatan(Long opdId, MasterJabatanStatus jabatanStatus);

    @Query("""
        SELECT m.*
        FROM master_jabatan m
        JOIN opd o ON o.id = m.opd_id
        WHERE o.kode_opd = :kodeOpd
          AND m.status_jabatan = :jabatanStatus
        ORDER BY m.nama_jabatan ASC
        """)
    List<MasterJabatan> findAllByKodeOpdAndStatusJabatanOrderByNamaJabatan(
        @Param("kodeOpd") String kodeOpd,
        @Param("jabatanStatus") MasterJabatanStatus jabatanStatus);

    boolean existsByOpdIdAndKodeJabatan(Long opdId, String kodeJabatan);

    Optional<MasterJabatan> findByKodeJabatan(String kodeJabatan);

    List<MasterJabatan> findByKodeJabatanIn(List<String> kodeJabatans);
}
