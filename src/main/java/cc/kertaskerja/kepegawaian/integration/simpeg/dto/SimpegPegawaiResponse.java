package cc.kertaskerja.kepegawaian.integration.simpeg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SimpegPegawaiResponse(
        @JsonProperty("pegawai_nip")
        String pegawaiNip,

        @JsonProperty("pegawai_nama")
        String pegawaiNama,

        @JsonProperty("pegawai_jenis_kelamin")
        String pegawaiJenisKelamin,

        @JsonProperty("pegawai_lahir_tempat")
        String pegawaiLahirTempat,

        @JsonProperty("pegawai_lahir_tanggal")
        String pegawaiLahirTanggal,

        @JsonProperty("pegawai_pensiun_tmt")
        String pegawaiPensiunTmt,

        @JsonProperty("pegawai_aktif_id")
        String pegawaiAktifId,

        @JsonProperty("pegawai_status")
        String pegawaiStatus,

        @JsonProperty("pegawai_jenis")
        String pegawaiJenis,

        @JsonProperty("pegawai_golongan_kode")
        String pegawaiGolonganKode,

        @JsonProperty("pegawai_golongan_nama")
        String pegawaiGolonganNama,

        @JsonProperty("pegawai_golongan_pangkat")
        String pegawaiGolonganPangkat,

        @JsonProperty("pegawai_golongan_tmt")
        String pegawaiGolonganTmt,

        @JsonProperty("pegawai_jenis_jabatan_kode")
        String pegawaiJenisJabatanKode,

        @JsonProperty("pegawai_jenis_jabatan_nama")
        String pegawaiJenisJabatanNama,

        @JsonProperty("pegawai_jabatan_kode")
        String pegawaiJabatanKode,

        @JsonProperty("pegawai_jabatan_terakhir")
        String pegawaiJabatanTerakhir,

        @JsonProperty("pegawai_eselon_kode")
        String pegawaiEselonKode,

        @JsonProperty("pegawai_eselon_nama")
        String pegawaiEselonNama,

        @JsonProperty("opd_kode")
        String opdKode,

        @JsonProperty("opd_level")
        String opdLevel,

        @JsonProperty("opd_nama")
        String opdNama
) {
}
