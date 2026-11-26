package cc.kertaskerja.kepegawaian.role.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleRepositoryCustom {
    Page<Role> findAll(Pageable pageable);
}
