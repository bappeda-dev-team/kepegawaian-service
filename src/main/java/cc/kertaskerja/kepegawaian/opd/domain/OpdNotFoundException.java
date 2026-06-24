package cc.kertaskerja.kepegawaian.opd.domain;

public class OpdNotFoundException extends RuntimeException {
    public OpdNotFoundException(String kodeOpd) {
        super("OPD dengan kode " + kodeOpd + " tidak ditemukan.");
    }

    public OpdNotFoundException(Long id) {
        super("OPD dengan id " + id + " tidak ditemukan");
    }
}
