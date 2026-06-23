package cc.kertaskerja.kepegawaian.pegawai.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PegawaiRepository extends CrudRepository<Pegawai, Long> {
    Long countByStatusPegawai(PegawaiStatus statusPegawai);
    Optional<Pegawai> findByPegawaiId(String pegawaiId);
    boolean existsByPegawaiId(String pegawaiId);
}
