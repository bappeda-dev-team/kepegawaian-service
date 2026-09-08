package cc.kertaskerja.kepegawaian.pegawai.domain;

import cc.kertaskerja.kepegawaian.identity.domain.CreateIdentityRequest;
import cc.kertaskerja.kepegawaian.identity.domain.IdentityAttributes;
import org.springframework.stereotype.Component;

@Component
public class IdentityMapper {

    public CreateIdentityRequest toCreateIdentityRequest(PegawaiIdentityData data) {
        Pegawai pegawai = data.pegawai();

        return new CreateIdentityRequest(
                pegawai.nip(),
                // WARNING HARD CODE EMAIL
                pegawai.nip() + "@kertaskerja.cc",
                pegawai.namaPegawai(),
                pegawai.namaPegawai(),
                true,
                new IdentityAttributes(
                        pegawai.nip(),
                        data.kodeOpd(),
                        data.namaOpd()
                )
        );
    }
}
