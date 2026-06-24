package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

public record JabatanPegawaiView(
        Long jabatanPegawaiId,
        String pegawaiId,
        String namaPegawai,
        String namaJabatan,
        String kodeOpd,
        String namaOpd
) {

}
