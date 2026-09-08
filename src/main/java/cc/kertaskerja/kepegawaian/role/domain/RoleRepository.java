package cc.kertaskerja.kepegawaian.role.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByKodeRole(String kodeRole);
    List<Role> findByIdIn(List<Long> roleIds);
    boolean existsByKodeRole(String kodeRole);
}
