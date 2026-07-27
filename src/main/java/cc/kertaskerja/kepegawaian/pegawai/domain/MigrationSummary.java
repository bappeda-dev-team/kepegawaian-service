package cc.kertaskerja.kepegawaian.pegawai.domain;

public record MigrationSummary(
        int total,
        int migrated,
        int linked,
        int failed
) {}
