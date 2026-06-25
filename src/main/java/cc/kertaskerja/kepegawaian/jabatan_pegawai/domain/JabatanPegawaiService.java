package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatan;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanNotFoundException;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanRepository;
import cc.kertaskerja.kepegawaian.opd.domain.Opd;
import cc.kertaskerja.kepegawaian.opd.domain.OpdNotFoundException;
import cc.kertaskerja.kepegawaian.opd.domain.OpdRepository;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiNotFoundException;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class JabatanPegawaiService {
    private final JabatanPegawaiRepository jabatanPegawaiRepository;
    private final MasterJabatanRepository masterJabatanRepository;
    private final OpdRepository opdRepository;
    private final PegawaiRepository pegawaiRepository;

    public JabatanPegawaiService(JabatanPegawaiRepository jabatanPegawaiRepository,
                                 MasterJabatanRepository masterJabatanRepository,
                                 OpdRepository opdRepository,
                                 PegawaiRepository pegawaiRepository
    ) {
        this.jabatanPegawaiRepository = jabatanPegawaiRepository;
        this.masterJabatanRepository = masterJabatanRepository;
        this.opdRepository = opdRepository;
        this.pegawaiRepository = pegawaiRepository;
    }

    @Transactional
    public JabatanPegawaiView tambahJabatan(JabatanPegawaiCreateCommand command) {

        // guard dasar, pegawai, opd, master jabatan exists
        findPegawai(command.pegawaiId());
        Opd opd = findOpd(command.opdId());
        MasterJabatan masterJabatan = findMasterJabatan(command.masterJabatanId());

        // guard jabatan utama pegawai lebih dari satu
        if (command.jenisPenugasan().isUtama()) {
            jabatanPegawaiRepository.findActivePrimaryByPegawaiId(command.pegawaiId())
                    .ifPresent(j -> {
                        throw new JabatanPegawaiAlreadyExistsException(j);
                    });
        }

        JabatanPegawai jabatanPegawaiBaru = JabatanPegawai.create(
                command.pegawaiId(),
                masterJabatan.id(),
                masterJabatan.namaJabatan(),
                opd.id(),
                opd.kodeOpd(),
                opd.namaOpd(),
                command.jenisPenugasan(),
                command.tmtMulai()
        );

        return jabatanPegawaiRepository.save(jabatanPegawaiBaru)
                .toJabatanPegawaiView();
    }

    @Transactional
    public JabatanPegawaiView akhiriJabatan(Long jabatanPegawaiId,
                                            JabatanPegawaiAlasanBerakhir alasanBerakhir,
                                            LocalDate tmtAkhir
    ) {
        // find jabatan target
        JabatanPegawai jabatanPegawai = jabatanPegawaiRepository.findById(jabatanPegawaiId)
                .orElseThrow(() -> new JabatanPegawaiNotFoundException(jabatanPegawaiId));

        // guard jabatan sudah tidak aktif
        if (jabatanPegawai.isNonAktif()) {
            throw new JabatanPegawaiAlreadyNonAktifException(jabatanPegawai);
        }

        JabatanPegawai jabatanNonAktif =
                jabatanPegawai.nonAktif(alasanBerakhir, tmtAkhir);

        jabatanNonAktif = jabatanPegawaiRepository.save(jabatanNonAktif);

        return jabatanNonAktif.toJabatanPegawaiView();
    }

    @Transactional
    public JabatanPegawaiView mutasiPegawai(
            Long pegawaiId,
            Long newMasterJabatanId,
            Long newOpdId,
            LocalDate tmtMutasi
    ) {
        // guard non aktifkan jabatan pegawai lama
        JabatanPegawai jabatanAktif = findCurrentJabatanPegawai(pegawaiId)
                .orElseThrow(JabatanPegawaiNotFoundException::new);
        // guard mutasi ke jabatan yang sama
        if (jabatanAktif.masterJabatanId().equals(newMasterJabatanId)
                && jabatanAktif.opdId().equals(newOpdId)
        ) {
            throw new JabatanPegawaiAlreadyExistsException(jabatanAktif);
        }

        // set jabatan sekarang to mutasi
        akhiriJabatan(
                jabatanAktif.id(),
                JabatanPegawaiAlasanBerakhir.MUTASI,
                tmtMutasi
        );

        // tambah jabatan baru
        return tambahJabatan(
                new JabatanPegawaiCreateCommand(
                        pegawaiId,
                        newMasterJabatanId,
                        newOpdId,
                        JabatanPegawaiJenisPenugasan.UTAMA,
                        tmtMutasi
                )
        );
    }

    @Transactional
    public void hardDelete(Long id) {
        jabatanPegawaiRepository.deleteById(id);
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

    private MasterJabatan findMasterJabatan(Long masterJabatanId) {
        return masterJabatanRepository.findById(masterJabatanId)
                .orElseThrow(() -> new MasterJabatanNotFoundException(masterJabatanId));
    }

    private Opd findOpd(Long opdId) {
        return opdRepository.findById(opdId)
                .orElseThrow(() -> new OpdNotFoundException(opdId));
    }

    // change to Pegawai if we need it
    private void findPegawai(Long pegawaiId) {
        pegawaiRepository.findById(pegawaiId)
                .orElseThrow(() -> new PegawaiNotFoundException(pegawaiId));
    }

}
