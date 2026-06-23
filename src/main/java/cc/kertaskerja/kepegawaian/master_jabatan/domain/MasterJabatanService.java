package cc.kertaskerja.kepegawaian.master_jabatan.domain;

import com.github.slugify.Slugify;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class MasterJabatanService {
    private final MasterJabatanRepository masterJabatanRepository;

    public MasterJabatanService(MasterJabatanRepository masterJabatanRepository) {
        this.masterJabatanRepository = masterJabatanRepository;
    }

    public List<MasterJabatanJenjang> listJenjangJabatan() {
        return List.of(MasterJabatanJenjang.values());
    }

    public List<MasterJabatanKategori> listKategoriJabatan() {
        return List.of(MasterJabatanKategori.values());
    }

    public List<MasterJabatanStatus> listStatusJabatan() {
        return List.of(MasterJabatanStatus.values());
    }

    public List<MasterJabatan> findAll() {
        return masterJabatanRepository.findAllByStatusJabatanOrderByNamaJabatan(MasterJabatanStatus.AKTIF);
    }

    public MasterJabatan findMasterJabatanById(Long id) {
        return masterJabatanRepository.findById(id)
                .orElseThrow(() -> new MasterJabatanNotFoundException(id));
    }

    @Transactional
    public MasterJabatan create(MasterJabatan masterJabatan) {
        String kodeJabatan = createKodeJabatan(masterJabatan);

        if (masterJabatanRepository.existsByKodeJabatan(kodeJabatan)) {
            throw new MasterJabatanAlreadyExistsException(masterJabatan.namaJabatan());
        }

        return masterJabatanRepository.save(MasterJabatan.of(
                kodeJabatan,
                masterJabatan.namaJabatan(),
                masterJabatan.jenjangJabatan(),
                MasterJabatanStatus.AKTIF
        ));
    }

    @Transactional
    public MasterJabatan update(Long id, MasterJabatan updatedMasterJabatan) {
        MasterJabatan existing = findMasterJabatanById(id);

        String kodeJabatan = createKodeJabatan(updatedMasterJabatan);

        masterJabatanRepository.findByKodeJabatan(kodeJabatan)
                .filter(mj -> !mj.id().equals(id))
                .ifPresent(mj -> {
                    throw new MasterJabatanAlreadyExistsException(kodeJabatan);
                });

        return masterJabatanRepository.save(
                existing.update(
                        updatedMasterJabatan.namaJabatan(),
                        updatedMasterJabatan.jenjangJabatan(),
                        kodeJabatan
                )
        );
    }

    @Transactional
    public String delete(Long id) {
        MasterJabatan jabatan = findMasterJabatanById(id);

        masterJabatanRepository.deleteById(id);

        return jabatan.namaJabatan();
    }

    private String createKodeJabatan(MasterJabatan jabatan) {
        final Slugify slg = Slugify.builder().build();
        String slug = slg.slugify(jabatan.namaJabatan());
        String suffix = jabatan.jenjangJabatan().name();

        return slug.toUpperCase(Locale.ROOT) + "-" + suffix.toUpperCase(Locale.ROOT);
    }
}
