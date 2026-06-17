package cc.kertaskerja.kepegawaian.opd.web;

public record OpdResponse(
        String kodeLembaga,
        String kodeOpd,
        String namaOpd,
        String singkatanOpd,
        String statusOpd
) {
}
