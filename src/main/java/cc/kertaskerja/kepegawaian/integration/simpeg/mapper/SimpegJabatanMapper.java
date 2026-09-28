package cc.kertaskerja.kepegawaian.integration.simpeg.mapper;

import cc.kertaskerja.kepegawaian.integration.simpeg.dto.SimpegJabatanResponse;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatan;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanJenjang;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanStatus;

public class SimpegJabatanMapper {

    public MasterJabatan toDomain(SimpegJabatanResponse source, Long opdId) {
        return new MasterJabatan(
                null,
                opdId,
                source.jabatanKode(),
                source.jabatanNama(),
                mapToJenjangJabatan(source),
                MasterJabatanStatus.AKTIF,
                null,
                null
        );
    }

    private MasterJabatanJenjang mapToJenjangJabatan(
            SimpegJabatanResponse source
    ) {
        if (source.jenisJabatanNama() == null || source.jenisJabatanNama().isBlank()) {
            return MasterJabatanJenjang.PELAKSANA;
        }

        String jenis = source.jenisJabatanNama().trim().toUpperCase();
        String jenjang = source.jenjangJabatanNama() == null
                ? ""
                : source.jenjangJabatanNama().trim().toUpperCase();

        String jabatan = source.jabatanNama() == null
                ? ""
                : source.jabatanNama().trim().toUpperCase();

        if (jenis.contains("STRUKTURAL")) {
            return mapStruktural(jabatan);
        }

        if (jenis.contains("PELAKSANA")) {
            return MasterJabatanJenjang.PELAKSANA;
        }

        if (jenis.contains("FUNGSIONAL TERTENTU")) {
            return switch (jenjang) {
                case "AHLI UTAMA" ->
                        MasterJabatanJenjang.FUNGSIONAL_TERTENTU_UTAMA;
                case "AHLI MADYA" ->
                        MasterJabatanJenjang.FUNGSIONAL_TERTENTU_MADYA;
                case "AHLI MUDA" ->
                        MasterJabatanJenjang.FUNGSIONAL_TERTENTU_MUDA;
                case "AHLI PERTAMA" ->
                        MasterJabatanJenjang.FUNGSIONAL_TERTENTU_PERTAMA;
                case "PENYELIA" ->
                        MasterJabatanJenjang.FUNGIONAL_TERTENTU_PENYELIA;
                case "MAHIR" ->
                        MasterJabatanJenjang.FUNGIONAL_TERTENTU_MAHIR;
                case "TERAMPIL" ->
                        MasterJabatanJenjang.FUNGIONAL_TERTENTU_TERAMPIL;
                case "PEMULA" ->
                        MasterJabatanJenjang.FUNGIONAL_TERTENTU_PEMULA;
                default ->
                        MasterJabatanJenjang.PELAKSANA;
            };
        }

        if (jenis.contains("FUNGSIONAL AHLI")) {
            return switch (jenjang) {
                case "AHLI UTAMA" ->
                        MasterJabatanJenjang.FUNGSIONAL_AHLI_UTAMA;
                case "AHLI MADYA" ->
                        MasterJabatanJenjang.FUNGSIONAL_AHLI_MADYA;
                case "AHLI MUDA" ->
                        MasterJabatanJenjang.FUNGSIONAL_AHLI_MUDA;
                case "AHLI PERTAMA" ->
                        MasterJabatanJenjang.FUNGSIONAL_AHLI_PERTAMA;
                default ->
                        MasterJabatanJenjang.PELAKSANA;
            };
        }

        if (jenis.contains("ADMINISTRASI")) {
            return switch (jenjang) {
                case "ADMINISTRATOR" ->
                        MasterJabatanJenjang.ADMINISTRATOR;
                case "PENGAWAS" ->
                        MasterJabatanJenjang.PENGAWAS;
                default ->
                        MasterJabatanJenjang.PELAKSANA;
            };
        }

        return MasterJabatanJenjang.PELAKSANA;
    }

    private MasterJabatanJenjang mapStruktural(String jabatanNama) {
        return switch (jabatanNama) {
            case "KEPALA DINAS" ->
                    MasterJabatanJenjang.KEPALA_DINAS;

            case "SEKRETARIS" ->
                    MasterJabatanJenjang.SEKRETARIS;

            case "KEPALA BIDANG" ->
                    MasterJabatanJenjang.KEPALA_BIDANG;

            case "KEPALA SUBBAGIAN" ->
                    MasterJabatanJenjang.KEPALA_SUBBAGIAN;

            default ->
                    MasterJabatanJenjang.PELAKSANA;
        };
    }
}
