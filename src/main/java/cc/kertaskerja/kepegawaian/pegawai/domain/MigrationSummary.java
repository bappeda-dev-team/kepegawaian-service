package cc.kertaskerja.kepegawaian.pegawai.domain;

public record MigrationSummary(
        int total,
        int created,
        int linked,
        int failed
) {}
