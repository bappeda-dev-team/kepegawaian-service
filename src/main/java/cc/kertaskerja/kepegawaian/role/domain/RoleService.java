package cc.kertaskerja.kepegawaian.role.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;
    // private final Logger log = LoggerFactory.getLogger(PegawaiService.class);

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Page<Role> findAllWithPage(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public List<Role> findAll() {
        return Streamable.of(roleRepository.findAll()).toList();
    }

    public List<Role> findNonAdminRole() {
        return roleRepository.findByIsAdminFalse();
    }

    public Role findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    @Transactional
    public Role create(Role.CreateCommand command) {
        String namaRole = command.namaRole();
        String kodeRole = kodeRoleMaker(command.namaRole());

        if (roleRepository.existsByNamaRoleIgnoreCase(namaRole)) {
            throw new RoleAlreadyExistsException(namaRole);
        }

        if (roleRepository.existsByKodeRole(kodeRole)) {
            throw new RoleAlreadyExistsException(kodeRole);
        }

        return roleRepository.save(
                Role.of(kodeRole,
                        namaRole,
                        command.isAdmin()));
    }

    @Transactional
    public Role update(Long id, Role.UpdateCommand command) {
        Role existingRole = findRoleById(id);

        if (roleRepository.existsByNamaRoleIgnoreCaseAndIdNot(
                command.namaRole(),
                id)) {
            throw new RoleAlreadyExistsException(command.namaRole());
        }

        return roleRepository.save(
                existingRole.update(
                        command.namaRole(),
                        command.isAdmin()));
    }

    @Transactional
    public String delete(Long id) {
        Role role = findRoleById(id);
        roleRepository.deleteById(id);
        return role.namaRole();
    }

    private String kodeRoleMaker(String namaRole) {
        return "ROLE-" + namaRole;
    }
}
