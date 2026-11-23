package cc.kertaskerja.kepegawaian.pegawai.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PegawaiRepositoryCustom {
    void upsertAll(List<Pegawai> pegawais);

    List<Pegawai> findDistinctByJabatanPegawaiOpdId(Long opdId);

    List<Pegawai> findDistinctByJabatanPegawaiKodeOpd(String kodeOpd);

    List<Pegawai> findDistinctByJabatanPegawaiOpdIdAndRoleId(
            Long opdId,
            Long roleId);

    List<Pegawai> findDistinctByJabatanPegawaiKodeOpdAndRoleId(
            String kodeOpd,
            Long roleId);

    List<Pegawai> findDistinctByJabatanPegawaiOpdIdAndRolePegawaiRoleNama(
            Long opdId,
            String roleNama);

    Page<Pegawai> findAll(Pageable pageable);
}
