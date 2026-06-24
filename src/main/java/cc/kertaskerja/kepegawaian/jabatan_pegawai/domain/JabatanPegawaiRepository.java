package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JabatanPegawaiRepository extends CrudRepository<JabatanPegawai, Long> {
    List<JabatanPegawai> findAllByPegawaiId(Long pegawaiId);
    List<JabatanPegawai> findAllByOpdId(Long opdId);
    List<JabatanPegawai> findAllByPegawaiIdAndOpdId(Long pegawaiId, Long opdId);

    Optional<JabatanPegawai> findActivePrimaryByPegawaiId(Long pegawaiId);
}
