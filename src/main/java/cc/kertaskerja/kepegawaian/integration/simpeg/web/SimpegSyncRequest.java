package cc.kertaskerja.kepegawaian.integration.simpeg.web;

public record SimpegSyncRequest(
        String syncFrom,
        Long opdId
) {
}
