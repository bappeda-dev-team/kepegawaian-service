package cc.kertaskerja.kepegawaian.integration.simpeg.mapper;

import cc.kertaskerja.kepegawaian.integration.simpeg.dto.SimpegJabatanPegawai;
import cc.kertaskerja.kepegawaian.integration.simpeg.dto.SimpegPegawaiResponse;
import cc.kertaskerja.kepegawaian.pegawai.domain.Pegawai;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SimpegPegawaiMapper {
    public Pegawai toDomain(SimpegPegawaiResponse source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate tanggalLahir = LocalDate.parse(source.pegawaiLahirTanggal(), formatter);
        return new Pegawai(
                null,
                source.pegawaiNip(),
                source.pegawaiNama(),
                source.pegawaiJenisKelamin(),
                source.pegawaiLahirTempat(),
                tanggalLahir,
                source.pegawaiStatus(),
                PegawaiStatus.AKTIF,
                "SYNC-FROM-SIMPEG",
                null,
                null,
                null
        );
    }

    public SimpegJabatanPegawai toJabatanPegawai(SimpegPegawaiResponse source, Long opdId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate mulaiJabatan = LocalDate.parse(source.pegawaiGolonganTmt(), formatter);
        return new SimpegJabatanPegawai(
                source.pegawaiNip(),
                opdId,
                source.pegawaiJabatanKode(),
                source.pegawaiJabatanTerakhir(),
                source.pegawaiJenisJabatanNama(),
                source.pegawaiEselonKode(),
                mulaiJabatan
        );
    }
}
