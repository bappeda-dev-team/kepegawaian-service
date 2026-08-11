package cc.kertaskerja.kepegawaian.role_pegawai.domain;

import cc.kertaskerja.kepegawaian.pegawai.domain.Pegawai;
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

        log.info(
                "Assign role requested roleId={} pegawaiId={}",
                roleId,
                pegawaiId
        );

        // guard, role must exist
        Role role = roleService.findRoleById(roleId);
        // guard, pegawai must exist
        Pegawai pegawai = pegawaiQueryService.findPegawaiById(pegawaiId);
        // guard, don't allow same role to be applied to same user
        if (rolePegawaiRepository.existsByRoleIdAndPegawaiId(roleId, pegawaiId)) {
            log.warn(
                    "Assigning role rejected roleId={} pegawaiId={} reason=already_assigned",
                    roleId,
                    pegawaiId
            );

            throw new RolePegawaiAlreadyExists();
        }

        RolePegawai saved = rolePegawaiRepository.save(rolePegawai);

        log.info(
                "Role assigned id={} roleId={} ({}) pegawaiId={} ({})",
                saved.id(),
                role.id(),
                role.namaRole(),
                pegawai.id(),
                pegawai.namaPegawai()
        );

        return new AssignRoleResult(saved, role);
    }

    public void delete(Long roleId, Long pegawaiId) {
        log.info(
                "Removing role {} from pegawai {}",
                roleId,
                pegawaiId
        );

        if (!rolePegawaiRepository.existsByRoleIdAndPegawaiId(roleId, pegawaiId)) {
            log.warn(
                    "Role assignment not found roleId={} pegawaiId={}",
                    roleId,
                    pegawaiId
            );

            throw new RolePegawaiNotFoundException();
        }
        rolePegawaiRepository.deleteByRoleIdAndPegawaiId(roleId, pegawaiId);

        log.info(
                "Role {} removed from pegawai {}",
                roleId,
                pegawaiId
        );
    }

}
