package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatan;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanService;
import cc.kertaskerja.kepegawaian.opd.domain.Opd;
import cc.kertaskerja.kepegawaian.opd.domain.OpdService;
import cc.kertaskerja.kepegawaian.pegawai.domain.Pegawai;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class JabatanPegawaiService {
    private final JabatanPegawaiRepository jabatanPegawaiRepository;
    private final MasterJabatanService masterJabatanService;
    private final OpdService opdService;
    private final PegawaiService pegawaiService;

    public JabatanPegawaiService(JabatanPegawaiRepository jabatanPegawaiRepository,
                                 MasterJabatanService masterJabatanService,
                                 OpdService opdService,
                                 PegawaiService pegawaiService
    ) {
        this.jabatanPegawaiRepository = jabatanPegawaiRepository;
        this.masterJabatanService = masterJabatanService;
        this.opdService = opdService;
        this.pegawaiService = pegawaiService;
    }

    @Transactional
    public JabatanPegawaiView create(JabatanPegawaiCreateCommand newJabatanPegawai) {

        // guard pegawai valid
        Pegawai pegawai = pegawaiService.findPegawaiById(newJabatanPegawai.pegawaiId());
        // guard jabatan aktif pegawai lebih dari satu in one opd
        if (newJabatanPegawai.jenisPenugasan().isUtama()) {
            jabatanPegawaiRepository.findActivePrimaryByPegawaiId(newJabatanPegawai.pegawaiId())
                    .ifPresent(j -> {
                        throw new JabatanPegawaiAlreadyExistsException(j);
                    });
        }
        // guard opd valid
        Opd opd = opdService.findOpdById(newJabatanPegawai.opdId());
        // guard  master jabatan valid
        MasterJabatan masterJabatan = masterJabatanService.findMasterJabatanById(newJabatanPegawai.masterJabatanId());

        JabatanPegawai jabatanPegawaiBaru = JabatanPegawai.create(
                newJabatanPegawai.pegawaiId(),
                masterJabatan.id(),
                masterJabatan.namaJabatan(),
                opd.id(),
                opd.kodeOpd(),
                opd.namaOpd(),
                newJabatanPegawai.jenisPenugasan(),
                newJabatanPegawai.tmtMulai()
        );

        return jabatanPegawaiRepository.save(jabatanPegawaiBaru)
                .toJabatanPegawaiView(pegawai);
    }

    @Transactional
    public void hardDelete(Long id) {
        jabatanPegawaiRepository.deleteById(id);
    }

    @Transactional
    public JabatanPegawai mutasi(
            Long pegawaiId,
            Long newMasterJabatanId,
            Long newOpdId,
            LocalDate tmtMutasi
    ) {
        // guard non aktifkan jabatan pegawai lama
        JabatanPegawai jabatanSekarang = findCurrentJabatanPegawai(pegawaiId)
                .orElseThrow(JabatanPegawaiNotFoundException::new);
        // guard mutasi ke jabatan yang sama
        if (
                jabatanSekarang.masterJabatanId().equals(newMasterJabatanId)
                        &&
                        jabatanSekarang.opdId().equals(newOpdId)
        ) {
            throw new JabatanPegawaiAlreadyExistsException(jabatanSekarang);
        }

        // set jabatan sekarang to mutasi
        jabatanPegawaiRepository.save(
                jabatanSekarang.mutasi(tmtMutasi)
        );

        // jabatan baru
        // find opd
        Opd opdBaru = opdService.findOpdById(newOpdId);
        // find jabatan baru
        MasterJabatan jabatanBaru = masterJabatanService.findMasterJabatanById(newMasterJabatanId);
        JabatanPegawai jabatanPegawaiBaru = JabatanPegawai.aktif(
               pegawaiId,
                jabatanBaru.id(),
                jabatanBaru.namaJabatan(),
                opdBaru.id(),
                opdBaru.kodeOpd(),
                opdBaru.namaOpd(),
                tmtMutasi
        );

        return jabatanPegawaiRepository.save(jabatanPegawaiBaru);
    }

    public JabatanPegawai findById(Long id) {
        return jabatanPegawaiRepository.findById(id)
                .orElseThrow(() -> new JabatanPegawaiNotFoundException(id));
    }

    // opd scope
    public List<JabatanPegawai> allJabatanPegawaiInOpd(Long opdId) {
        return jabatanPegawaiRepository.findAllByOpdId(opdId);
    }

    public List<JabatanPegawai> allHistoryPegawaiInOpd(Long pegawaiId, Long opdId) {
        return jabatanPegawaiRepository.findAllByPegawaiIdAndOpdId(pegawaiId, opdId);
    }

    // pegawai scope
    public List<JabatanPegawai> allHistoryJabatanPegawai(Long pegawaiId) {
        return jabatanPegawaiRepository.findAllByPegawaiId(pegawaiId);
    }

    public Optional<JabatanPegawai> findCurrentJabatanPegawai(Long pegawaiId) {
        return jabatanPegawaiRepository.findActivePrimaryByPegawaiId(pegawaiId);
    }

}
