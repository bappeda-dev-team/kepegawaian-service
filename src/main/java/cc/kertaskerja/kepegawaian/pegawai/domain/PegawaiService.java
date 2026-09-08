package cc.kertaskerja.kepegawaian.pegawai.domain;

import cc.kertaskerja.kepegawaian.config.IdentityProperties;
import cc.kertaskerja.kepegawaian.identity.domain.CreateUserResult;
import cc.kertaskerja.kepegawaian.identity.domain.IdentityService;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawai;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiNotFoundException;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiRepository;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiView;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.AssignRoleResult;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.RolePegawaiNotFoundException;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.RolePegawaiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PegawaiService {
    private final PegawaiRepository pegawaiRepository;
    private final JabatanPegawaiRepository jabatanPegawaiRepository;
    private final IdentityService identityService;
    private final IdentityMapper identityMapper;
    private final IdentityProperties identityProperties;
    private final Logger log = LoggerFactory.getLogger(PegawaiService.class);
    private final RolePegawaiService rolePegawaiService;

    private static final String DEFAULT_KODE_OPD = "0.00.0.00.0.0000";
    private static final String DEFAULT_NAMA_OPD = "UNKNOWN";

    public PegawaiService(PegawaiRepository pegawaiRepository,
                          JabatanPegawaiRepository jabatanPegawaiRepository,
                          IdentityService identityService,
                          IdentityMapper identityMapper,
                          IdentityProperties identityProperties,
                          RolePegawaiService rolePegawaiService
    ) {
        this.pegawaiRepository = pegawaiRepository;
        this.jabatanPegawaiRepository = jabatanPegawaiRepository;
        this.identityService = identityService;
        this.identityMapper = identityMapper;
        this.identityProperties = identityProperties;
        this.rolePegawaiService = rolePegawaiService;
    }

    public List<Pegawai> findAll() {
        return Streamable.of(
                pegawaiRepository.findAll()
        ).toList();
    }

    public PegawaiView findPegawaiByPegawaiId(String nip) {
        Pegawai pegawai =  pegawaiRepository.findByNip(nip)
                .orElseThrow(() -> new PegawaiNotFoundException(nip));

        JabatanPegawaiView jabatanPegawai = jabatanPegawaiRepository.findAllByPegawaiId(pegawai.id())
                .stream().filter(JabatanPegawai::isAktif)
                .map(JabatanPegawai::toJabatanPegawaiView)
                .findFirst()
                .orElseThrow(JabatanPegawaiNotFoundException::new);

        AssignRoleResult role = rolePegawaiService.findAllByPegawaiId(pegawai.id())
                .stream()
                .findFirst()
                .orElseThrow(RolePegawaiNotFoundException::new);

        return new PegawaiView(pegawai, jabatanPegawai, role);
    }


    public Pegawai findPegawaiById(Long id) {
        return pegawaiRepository.findById(id)
                .orElseThrow(()-> new PegawaiNotFoundException(id));
    }

    public PegawaiDetails findHistoriPegawai(Long pegawaiId, PegawaiJenisHistori jenisHistori, Integer bulan, Integer tahun) {
        // guard pegawai
        Pegawai pegawai = findPegawaiById(pegawaiId);

        List<JabatanPegawaiView> jabatanPegawais = jabatanPegawaiRepository.findAllByPegawaiId(pegawaiId)
                .stream().map(JabatanPegawai::toJabatanPegawaiView).toList();

        List<AssignRoleResult> rolePegawais = rolePegawaiService.findAllByPegawaiId(pegawaiId);

        return new PegawaiDetails(
                pegawai.id(),
                pegawai.nip(),
                pegawai.namaPegawai(),
                jabatanPegawais,
                rolePegawais
        );
    }

    @Transactional
    public Pegawai create(Pegawai newPegawai, String initialPassword) {
        String nip = newPegawai.nip();

        if (pegawaiRepository.existsByNip(nip)) {
            throw new PegawaiAlreadyExistsException(nip);
        }

        return pegawaiRepository.save(newPegawai);
    }

    @Transactional
    public Pegawai update(Long id, Pegawai updatePegawai) {
        Pegawai existingPegawai = findPegawaiById(id);

        pegawaiRepository.findByNip(updatePegawai.nip())
                .filter(p -> !p.id().equals(id))
                .ifPresent(opd -> {
                    throw new PegawaiNotFoundException(updatePegawai.nip());
                });

        return pegawaiRepository.save(
                existingPegawai.update(
                        updatePegawai.nip(),
                        updatePegawai.namaPegawai(),
                        updatePegawai.statusPegawai()
        ));
    }

    @Transactional
    public String delete(Long id) {
        Pegawai pegawai = findPegawaiById(id);
        pegawaiRepository.deleteById(id);
        return pegawai.namaPegawai();
    }

    @Transactional
    public MigrationSummary migratePegawaiToKeycloak() {

        List<Pegawai> pegawais = findAll();

        List<PegawaiIdentityData> identities = mapToIdentityData(pegawais);

        int created = 0;
        int failed = 0;
        int linked = 0;

        for (PegawaiIdentityData identity : identities) {
            try {
                MigrationResult result = migratePegawai(identity);
                if (result == MigrationResult.CREATED) {
                    created++;
                } else if (result == MigrationResult.LINKED) {
                    linked++;
                }
            } catch (Exception ex) {
                failed++;

                log.error(
                        "Failed migrating pegawai id={}, nip={}",
                        identity.pegawai().id(),
                        identity.pegawai().nip(),
                        ex
                );
            }
        }

        log.info(
                "Migration finished. total={}, created={}, linked={}, failed={}",
                pegawais.size(),
                created,
                linked,
                failed
        );

        return new MigrationSummary(
                pegawais.size(),
                created,
                linked,
                failed
        );
    }

    // mapping keycloak attributes from pegawai records
    private List<PegawaiIdentityData> mapToIdentityData(
            List<Pegawai> pegawais
    ) {
        List<Long> pegawaiIds = pegawais.stream()
                .map(Pegawai::id)
                .toList();

        Map<Long, JabatanPegawai> jabatanByPegawaiId =
                jabatanPegawaiRepository
                        .findActivePrimaryByPegawaiIdIn(pegawaiIds)
                        .stream()
                        .collect(Collectors.toMap(
                                JabatanPegawai::pegawaiId,
                                Function.identity()
                        ));

        List<AssignRoleResult> assignments =
                rolePegawaiService.findAllByPegawaiIdIn(pegawaiIds);

        Map<Long, Set<String>> rolesByPegawaiId =
                assignments.stream()
                        .collect(Collectors.groupingBy(
                                result -> result.assignment().pegawaiId(),
                                Collectors.mapping(
                                        result -> result.role().namaRole(),
                                        Collectors.toSet()
                                )
                        ));

        return pegawais.stream()
                .map(pegawai -> {
                    JabatanPegawai jabatan =
                            jabatanByPegawaiId.get(pegawai.id());

                    return new PegawaiIdentityData(
                            pegawai,
                            jabatan != null
                                    ? jabatan.kodeOpd()
                                    : DEFAULT_KODE_OPD,
                            jabatan != null
                                    ? jabatan.namaOpd()
                                    : DEFAULT_NAMA_OPD,
                            rolesByPegawaiId.getOrDefault(
                                    pegawai.id(),
                                    Set.of()
                            )
                    );
                })
                .toList();
    }

    protected MigrationResult migratePegawai(PegawaiIdentityData data) {
        CreateUserResult result = identityService.createUser(
                identityMapper.toCreateIdentityRequest(data)
        );

        if (result.created()) {
            identityService.resetPassword(
                    result.userId(),
                    identityProperties.migration().defaultPassword(),
                    identityProperties.migration().temporaryPassword()
            );
        }

        log.info("ASSIGN ROLES FOR pegawai={}", data.pegawai().namaPegawai());

        identityService.assignRoles(
                result.userId(),
                data.roles()
        );

        // update pegawai keycloak id
        linkKeycloakUser(data.pegawai(), result.userId());

        return result.created()
                ? MigrationResult.CREATED
                : MigrationResult.LINKED;
    }

    @Transactional
    protected void linkKeycloakUser(
            Pegawai pegawai,
            String userId
    ) {
        pegawaiRepository.save(
                pegawai.withKeycloakUserId(userId)
        );
    }
}
