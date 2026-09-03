package cc.kertaskerja.kepegawaian.role_pegawai.domain;

import cc.kertaskerja.kepegawaian.role.domain.Role;
import cc.kertaskerja.kepegawaian.role.domain.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RolePegawaiService {
    private final RolePegawaiRepository rolePegawaiRepository;
    private final RoleService roleService;

    public RolePegawaiService(RolePegawaiRepository rolePegawaiRepository, RoleService roleService) {
        this.rolePegawaiRepository = rolePegawaiRepository;
        this.roleService = roleService;
    }

    public RolePegawai findRole(Long roleId, Long pegawaiId) {
        return rolePegawaiRepository.findByRoleIdAndPegawaiId(roleId, pegawaiId)
                .orElseThrow(RolePegawaiNotFoundException::new);
    }

    public List<AssignRoleResult> findAllByPegawaiId(Long pegawaiId) {
        return rolePegawaiRepository.findByPegawaiId(pegawaiId).stream()
                .map(rolePeg -> {
                    Role role = roleService.findRoleById(rolePeg.roleId());
                    return new AssignRoleResult(rolePeg, role);
                }).toList();
    }

    public List<RolePegawai> findPegawaiByRoleName(String namaRole) {
        Role role = roleService.findRoleByNamaRole(namaRole);
        return rolePegawaiRepository.findByRoleId(role.id());
    }
}
