package cc.kertaskerja.kepegawaian.master_jabatan.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MasterJabatanRepository extends CrudRepository<MasterJabatan, Long> {
    List<MasterJabatan> findAllByStatusJabatanOrderByNamaJabatan(MasterJabatanStatus jabatanStatus);

    boolean existsByKodeJabatan(String kodeJabatan);

    Optional<MasterJabatan> findByKodeJabatan(String kodeJabatan);
}
