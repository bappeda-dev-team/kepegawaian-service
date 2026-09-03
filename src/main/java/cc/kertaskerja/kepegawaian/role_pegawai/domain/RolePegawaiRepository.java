package cc.kertaskerja.kepegawaian.role_pegawai.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RolePegawaiRepository extends CrudRepository<RolePegawai, Long> {
    List<RolePegawai> findByPegawaiId(Long pegawaiId);

    List<RolePegawai> findByRoleId(Long roleId);

    void deleteByRoleIdAndPegawaiId(Long roleId, Long pegawaiId);

    Optional<RolePegawai> findByRoleIdAndPegawaiId(Long roleId, Long pegawaiId);

    boolean existsByRoleIdAndPegawaiId(Long roleId, Long pegawaiId);
}
