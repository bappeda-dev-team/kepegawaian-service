package cc.kertaskerja.kepegawaian.role_pegawai.domain;

import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiQueryService;
import cc.kertaskerja.kepegawaian.role.domain.Role;
import cc.kertaskerja.kepegawaian.role.domain.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleAssignmentService {
    private static final Logger log = LoggerFactory.getLogger(RoleAssignmentService.class);
    private final RolePegawaiRepository rolePegawaiRepository;
    private final RoleService roleService;
    private final PegawaiQueryService pegawaiQueryService;

    public RoleAssignmentService(RolePegawaiRepository rolePegawaiRepository,
                                 RoleService roleService, PegawaiQueryService pegawaiQueryService) {
        this.rolePegawaiRepository = rolePegawaiRepository;
        this.roleService = roleService;
        this.pegawaiQueryService = pegawaiQueryService;
    }

    public AssignRoleResult assign(RolePegawai rolePegawai) {
        Long roleId = rolePegawai.roleId();
        Long pegawaiId = rolePegawai.pegawaiId();

        // guard, role must exist
        Role role = roleService.findRoleById(roleId);

        // guard, pegawai must exists
        pegawaiQueryService.findPegawaiById(pegawaiId);

        // guard, don't allow same role to be applied to same user
        if (rolePegawaiRepository.existsByRoleIdAndPegawaiId(roleId, pegawaiId)) {
            throw new RolePegawaiAlreadyExists();
        }

        RolePegawai saved = rolePegawaiRepository.save(rolePegawai);

        return new AssignRoleResult(saved, role);
    }

    public void delete(Long roleId, Long pegawaiId) {

        if (!rolePegawaiRepository.existsByRoleIdAndPegawaiId(roleId, pegawaiId)) {
            log.warn(
                    "Role assignment not found roleId={} pegawaiId={}",
                    roleId,
                    pegawaiId
            );

            throw new RolePegawaiNotFoundException();
        }
        rolePegawaiRepository.deleteByRoleIdAndPegawaiId(roleId, pegawaiId);
    }

}
