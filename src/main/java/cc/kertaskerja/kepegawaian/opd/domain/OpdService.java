package cc.kertaskerja.kepegawaian.opd.domain;

import cc.kertaskerja.kepegawaian.config.KertaskerjaProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OpdService {

    private final OpdRepository opdRepository;
    private final KertaskerjaProperties kertaskerjaProperties;

    public OpdService(OpdRepository opdRepository, KertaskerjaProperties kertaskerjaProperties) {
        this.opdRepository = opdRepository;
        this.kertaskerjaProperties = kertaskerjaProperties;
    }

    public List<Opd> findAllOpdAktifInLembaga() {
        return opdRepository
                .findAllByKodeLembagaAndStatusOpdOrderByNamaOpdAsc(
                        kertaskerjaProperties.kodeLembaga(),
                        OpdStatus.AKTIF
                );
    }

    public Opd findOpdByKodeOpd(String kodeOpd) {
        if (kodeOpd == null || kodeOpd.isBlank()) {
            throw new IllegalArgumentException("Kode OPD tidak boleh kosong");
        }

        return opdRepository.findByKodeOpd(kodeOpd)
                .orElseThrow(() -> new OpdNotFoundException(kodeOpd));
    }

    public Opd findOpdById(Long id) {
        return opdRepository.findById(id)
                .orElseThrow(() -> new OpdNotFoundException(id));
    }

    @Transactional
    public Opd save(Opd newOpd) {
        String kodeOpd = newOpd.kodeOpd();

        // guard
        if (opdRepository.existsByKodeOpd(kodeOpd)) {
            throw new OpdAlreadyExistsException(kodeOpd);
        }

        return opdRepository.save(Opd.of(
                kertaskerjaProperties.kodeLembaga(),
                kodeOpd,
                newOpd.namaOpd(),
                newOpd.singkatanOpd()
        ));
    }

    @Transactional
    public Opd update(Long id, Opd updatedOpd) {
        Opd existingOpd = findOpdById(id);

        opdRepository.findByKodeOpd(updatedOpd.kodeOpd())
                .filter(opd -> !opd.Id().equals(id))
                .ifPresent(opd -> {
                    throw new OpdAlreadyExistsException(updatedOpd.kodeOpd());
                });

        return opdRepository.save(
                existingOpd.update(
                        updatedOpd.kodeOpd(),
                        updatedOpd.namaOpd(),
                        updatedOpd.singkatanOpd()
                ));
    }

    @Transactional
    public void delete(Long id) {
        // guard opd not found
        findOpdById(id);
        opdRepository.deleteById(id);
    }
}
