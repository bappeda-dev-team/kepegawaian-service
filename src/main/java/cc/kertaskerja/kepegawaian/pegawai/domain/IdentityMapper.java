package cc.kertaskerja.kepegawaian.pegawai.domain;

import cc.kertaskerja.kepegawaian.identity.domain.CreateIdentityRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IdentityMapper {

    public CreateIdentityRequest toCreateIdentityRequest(Pegawai pegawai, String kodeOpd) {
        // WARNING HARD CODE EMAIL
        String email = pegawai.nip() + "@kertaskerja.cc";
        String lastName = pegawai.namaPegawai();
        return new CreateIdentityRequest(
                pegawai.nip(),
                email,
                pegawai.namaPegawai(),
                lastName,
                true,
                Map.of(
                        "nip", List.of(pegawai.nip()),
                        "kode_opd", List.of(kodeOpd)
                )
        );
    }
}
