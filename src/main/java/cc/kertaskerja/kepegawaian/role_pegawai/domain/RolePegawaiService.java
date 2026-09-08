package cc.kertaskerja.kepegawaian.role_pegawai.domain;

import cc.kertaskerja.kepegawaian.role.domain.Role;
import cc.kertaskerja.kepegawaian.role.domain.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RolePegawaiService {
    private final RolePegawaiRepository rolePegawaiRepository;
    private final RoleRepository roleRepository;

    public RolePegawaiService(
            RolePegawaiRepository rolePegawaiRepository,
            RoleRepository roleRepository
    ) {
        this.rolePegawaiRepository = rolePegawaiRepository;
        this.roleRepository = roleRepository;
    }

    public RolePegawai findRole(Long roleId, Long pegawaiId) {
        return rolePegawaiRepository.findByRoleIdAndPegawaiId(roleId, pegawaiId)
                .orElseThrow(RolePegawaiNotFoundException::new);
    }

    public List<AssignRoleResult> findAllByPegawaiId(Long pegawaiId) {
        return rolePegawaiRepository.findByPegawaiId(pegawaiId).stream()
                .map(rolePeg -> {
                    Role role = roleRepository.findById(rolePeg.roleId())
                            .orElseThrow(RolePegawaiNotFoundException::new);
                    return new AssignRoleResult(rolePeg, role);
                }).toList();
    }

   public List<AssignRoleResult> findAllByPegawaiIdIn(List<Long> pegawaiIds
    ) {
        List<RolePegawai> assignments =
                rolePegawaiRepository.findByPegawaiIdIn(pegawaiIds);

        List<Long> roleIds = assignments.stream()
                .map(RolePegawai::roleId)
                .distinct()
                .toList();

        Map<Long, Role> rolesById =
                roleRepository.findByIdIn(roleIds)
                        .stream()
                        .collect(Collectors.toMap(
                                Role::id,
                                Function.identity()
                        ));

        return assignments.stream()
                .map(assignment -> {
                    Role role = rolesById.get(assignment.roleId());

                    if (role == null) {
                        throw new IllegalStateException(
                                "Role not found: " + assignment.roleId()
                        );
                    }

                    return new AssignRoleResult(
                            assignment,
                            role
                    );
                })
                .toList();
    }
}
