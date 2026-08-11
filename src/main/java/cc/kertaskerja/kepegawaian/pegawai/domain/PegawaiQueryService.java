package cc.kertaskerja.kepegawaian.pegawai.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PegawaiQueryService {
    private final PegawaiRepository pegawaiRepository;

    public PegawaiQueryService(PegawaiRepository pegawaiRepository) {
        this.pegawaiRepository = pegawaiRepository;
    }

    public Pegawai findPegawaiById(Long id) {
        return pegawaiRepository.findById(id)
                .orElseThrow(()-> new PegawaiNotFoundException(id));
    }

}
