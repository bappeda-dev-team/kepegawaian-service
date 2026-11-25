package cc.kertaskerja.kepegawaian.master_jabatan.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MasterJabatanRepositoryCustom {
    Page<MasterJabatan> findAll(Pageable pageable);

    Page<MasterJabatan> findAllByKodeOpdAndStatusJabatan(
            Pageable pageable,
            String kodeOpd,
            MasterJabatanStatus jabatanStatus);
}
