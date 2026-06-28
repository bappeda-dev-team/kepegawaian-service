package cc.kertaskerja.kepegawaian.pegawai.domain;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawai;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiRepository;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiView;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PegawaiService {
    private final PegawaiRepository pegawaiRepository;
    private final JabatanPegawaiRepository jabatanPegawaiRepository;

    public PegawaiService(PegawaiRepository pegawaiRepository,
                          JabatanPegawaiRepository jabatanPegawaiRepository
    ) {
        this.pegawaiRepository = pegawaiRepository;
        this.jabatanPegawaiRepository = jabatanPegawaiRepository;
    }

    public List<Pegawai> findAll() {
        // TODO: implement pagination
        return Streamable.of(
                pegawaiRepository.findAll()
                ).stream().limit(20).toList();
    }

    public Pegawai findPegawaiByPegawaiId(String pegawaiId) {
        return pegawaiRepository.findByNip(pegawaiId)
                .orElseThrow(() -> new PegawaiNotFoundException(pegawaiId));
    }

    public Pegawai findPegawaiById(Long id) {
        return pegawaiRepository.findById(id)
                .orElseThrow(()-> new PegawaiNotFoundException(id));
    }

    @Transactional
    public Pegawai create(Pegawai newPegawai) {
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

    public PegawaiDetails findHistoriPegawai(Long pegawaiId, PegawaiJenisHistori jenisHistori, Integer bulan, Integer tahun) {
        // guard pegawai
        Pegawai pegawai = findPegawaiById(pegawaiId);

        List<JabatanPegawaiView> jabatanPegawais = jabatanPegawaiRepository.findAllByPegawaiId(pegawaiId)
                .stream().map(JabatanPegawai::toJabatanPegawaiView).toList();

        return new PegawaiDetails(
                pegawai.nip(),
                pegawai.namaPegawai(),
                jabatanPegawais
        );
    }
}
