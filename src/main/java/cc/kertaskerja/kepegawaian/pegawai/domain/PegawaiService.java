package cc.kertaskerja.kepegawaian.pegawai.domain;

import cc.kertaskerja.kepegawaian.pegawai.web.PegawaiHistoriQuery;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PegawaiService {
    private final PegawaiRepository pegawaiRepository;

    public PegawaiService(PegawaiRepository pegawaiRepository) {
        this.pegawaiRepository = pegawaiRepository;
    }

    public List<Pegawai> findAll() {
        return Streamable.of(
                pegawaiRepository.findAll()
                ).toList();
    }

    public Pegawai findPegawaiByPegawaiId(String pegawaiId) {
        return pegawaiRepository.findByPegawaiId(pegawaiId)
                .orElseThrow(() -> new PegawaiNotFoundException(pegawaiId));
    }

    public Pegawai findPegawaiById(Long id) {
        return pegawaiRepository.findById(id)
                .orElseThrow(()-> new PegawaiNotFoundException(id));
    }

    @Transactional
    public Pegawai create(Pegawai newPegawai) {
        String pegawaiId = newPegawai.pegawaiId();

        if (pegawaiRepository.existsByPegawaiId(pegawaiId)) {
            throw new PegawaiAlreadyExistsException(pegawaiId);
        }

        return pegawaiRepository.save(newPegawai);
    }

    @Transactional
    public Pegawai update(Long id, Pegawai updatePegawai) {
        Pegawai existingPegawai = findPegawaiById(id);

        pegawaiRepository.findByPegawaiId(updatePegawai.pegawaiId())
                .filter(p -> !p.id().equals(id))
                .ifPresent(opd -> {
                    throw new PegawaiNotFoundException(updatePegawai.pegawaiId());
                });

        return pegawaiRepository.save(
                existingPegawai.update(
                        updatePegawai.pegawaiId(),
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

    public List<Pegawai> findHistoriPegawai(Long pegawaiId, PegawaiJenisHistori jenisHistori, Integer bulan, Integer tahun) {
       // TODO: Implementasi query histori pegawai
        return findAll();
    }
}
