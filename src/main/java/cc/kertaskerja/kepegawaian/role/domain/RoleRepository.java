package cc.kertaskerja.kepegawaian.role.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByKodeRole(String kodeRole);
    Optional<Role> findByNamaRole(String namaRole);
    boolean existsByKodeRole(String kodeRole);
}
