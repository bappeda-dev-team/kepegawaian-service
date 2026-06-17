package cc.kertaskerja.kepegawaian.opd.domain;

import cc.kertaskerja.kepegawaian.config.KertaskerjaProperties;
import cc.kertaskerja.kepegawaian.opd.web.OpdResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpdService {

    private final OpdRepository opdRepository;
    private final KertaskerjaProperties kertaskerjaProperties;

    public OpdService(OpdRepository opdRepository, KertaskerjaProperties kertaskerjaProperties) {
        this.opdRepository = opdRepository;
        this.kertaskerjaProperties = kertaskerjaProperties;
    }

    public Iterable<Opd> findAll() {
       return opdRepository.findAll();
    }

    public Iterable<Opd> findAllOpdInLembaga() {
        return opdRepository.findAllByKodeLembagaOrderByNamaOpdAsc(kertaskerjaProperties.kodeLembaga());
    }

    public List<OpdResponse> findAllOpdAktifInLembaga() {
        return opdRepository
                .findAllByKodeLembagaAndStatusOpdOrderByNamaOpdAsc(
                        kertaskerjaProperties.kodeLembaga(),
                        OpdStatus.AKTIF
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Opd findOpdByKodeOpd(String kodeOpd) {
        if (kodeOpd == null) {
            throw new IllegalArgumentException("Kode OPD tidak boleh kosong");
        }

        return opdRepository.findByKodeOpd(kodeOpd)
                .orElseThrow(() -> new OpdNotFoundException(kodeOpd));
    }

    public Opd save(Opd opd) {
        String kodeOpd = opd.kodeOpd();
        if (opdRepository.existsByKodeOpd(kodeOpd)) {
            throw new OpdAlreadyExistsException(kodeOpd);
        }

        Opd savedOpd = Opd.of(
                kertaskerjaProperties.kodeLembaga(),
                kodeOpd,
                opd.namaOpd(),
                opd.singkatanOpd()
        );

        return opdRepository.save(savedOpd);
    }

    public void delete(String kodeOpd) {
        opdRepository.delete(findOpdByKodeOpd(kodeOpd));
    }

    public Opd updateOrCreate(Opd opd) {
        return opdRepository.findByKodeOpd(opd.kodeOpd()).map(
                existingOpd -> {
                    Opd opdToUpdate = new Opd(
                            existingOpd.Id(),
                            existingOpd.kodeLembaga(),
                            opd.kodeOpd(),
                            opd.namaOpd(),
                            opd.singkatanOpd(),
                            opd.statusOpd(),
                            existingOpd.createdDate(),
                            null
                    );
                    return opdRepository.save(opdToUpdate);
                }
        ).orElseGet(() -> save(opd));
    }

    private OpdResponse toResponse(Opd opd) {
        return new OpdResponse(
                opd.kodeLembaga(),
                opd.kodeOpd(),
                opd.namaOpd(),
                opd.singkatanOpd(),
                opd.statusOpd().name()
        );
    }
}
