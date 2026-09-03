package cc.kertaskerja.kepegawaian.pegawai.domain;

import cc.kertaskerja.kepegawaian.config.IdentityProperties;
import cc.kertaskerja.kepegawaian.identity.domain.IdentityService;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawai;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiNotFoundException;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiRepository;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiView;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.AssignRoleResult;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.RolePegawai;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.RolePegawaiNotFoundException;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.RolePegawaiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        // TODO: implement pagination
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

    public List<Pegawai> findPegawaiByRoleName(String namaRole) {
        List<RolePegawai> rolePegawais = rolePegawaiService.findPegawaiByRoleName(namaRole);
        return rolePegawais.stream()
                .map(rp -> pegawaiRepository.findById(rp.pegawaiId())
                        .orElseThrow(() -> new PegawaiNotFoundException(rp.pegawaiId())))
                .toList();
    }

    @Transactional
    public Pegawai create(Pegawai newPegawai, String initialPassword) {
        String nip = newPegawai.nip();

        if (pegawaiRepository.existsByNip(nip)) {
            throw new PegawaiAlreadyExistsException(nip);
        }

        Pegawai savedPegawai = pegawaiRepository.save(newPegawai);
        // TODO UPDATE CREATE REQUEST TO ACCEPT OPD
        String kodeOpd = "0.00.0.00.0.0000";

        String userId = identityService.createUser(
                identityMapper.toCreateIdentityRequest(savedPegawai, kodeOpd)
        );

        identityService.resetPassword(
                userId,
                initialPassword,
                true // wajib ganti password saat login pertama
        );

        Pegawai updatedPegawai = savedPegawai.withKeycloakUserId(userId);

        return pegawaiRepository.save(updatedPegawai);
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

        List<Pegawai> pegawais = pegawaiRepository.findWithoutKeycloakUserId();

        int success = 0;
        int failed = 0;
        int linked = 0;

        for (Pegawai pegawai : pegawais) {
            try {
                migratePegawai(pegawai);
                success++;
            } catch (Exception ex) {
                failed++;

                log.error(
                        "Failed migrating pegawai id={}, nip={}",
                        pegawai.id(),
                        pegawai.nip(),
                        ex
                );
            }
        }

        log.info("Migration finished");

        return new MigrationSummary(
                pegawais.size(),
                success,
                linked,
                failed
        );
    }

    @Transactional
    protected void migratePegawai(Pegawai pegawai) {

        String kodeOpd = jabatanPegawaiRepository
                .findActivePrimaryByPegawaiId(pegawai.id())
                .map(JabatanPegawai::kodeOpd)
                .orElse("0.00.0.00.0.0000");

        String userId = identityService.createUser(
                identityMapper.toCreateIdentityRequest(pegawai, kodeOpd)
        );

        identityService.resetPassword(
                userId,
                identityProperties.migration().defaultPassword(),
                identityProperties.migration().temporaryPassword()
        );

        pegawaiRepository.save(
                pegawai.withKeycloakUserId(userId)
        );
    }
}
