package cc.kertaskerja.kepegawaian.role.domain;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;
    //private final Logger log = LoggerFactory.getLogger(PegawaiService.class);

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll().toList();
    }

    public Role findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Transactional
    public Role create(Role newRole) {
        String kodeRole = kodeRoleMaker(newRole.namaRole());
        if (newRole.existsByKodeRole(kodeRole)) {
            throw new RoleAlreadyExistsException(kodeRole);
        }

        return roleRepository.save(newRole);
    }

    @Transactional
    public Role update(Long id, Role updateRole) {
        Role existingRole = findRoleById(id);

        roleRepository.findByKodeRole(updateRole.kodeRole())
            .filter(r -> !r.id().equals(id))
            .ifPresent(role -> {
            throw new RoleNotFoundException(updateRole.kodeRole());
        });

        return roleRepository.save(
            existingRole.update(
                updateRole.kodeRole(),
                updateRole.namaRole()
        ));
    }

    @Transactional
    public String delete(Long id) {
        Role role = findRolebyId(id);
        roleRepository.deleteById(id);
        return role.namaRole();
    }


    private String kodeRoleMaker(String namaRole) {
        return "ROLE-" + namaRole;
    }
}
