package cc.kertaskerja.kepegawaian.opd;

import cc.kertaskerja.kepegawaian.config.KertaskerjaProperties;
import cc.kertaskerja.kepegawaian.opd.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpdServiceTest {

    @Mock
    private OpdRepository opdRepository;

    @Mock
    private KertaskerjaProperties properties;

    @InjectMocks
    private OpdService opdService;

    @Test
    void shouldReturnActiveOpdList() {
        when(properties.kodeLembaga()).thenReturn("63.11");

        var opd = Opd.of(
                "63.11",
                "5.01.5.05.0.00.01.0000",
                "Badan Kepegawaian",
                "BKPSDM"
        );

        when(opdRepository
                .findAllByKodeLembagaAndStatusOpdOrderByNamaOpdAsc(
                        "63.11",
                        OpdStatus.AKTIF
                ))
                .thenReturn(List.of(opd));

        var result = opdService.findAllOpdAktifInLembaga();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().namaOpd())
                .isEqualTo("Badan Kepegawaian");
    }

    @Test
    void shouldThrowExceptionWhenOpdNotFound() {
        when(opdRepository.findByKodeOpd("5.01"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                opdService.findOpdByKodeOpd("5.01"))
                .isInstanceOf(OpdNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenKodeOpdAlreadyExists() {
        var opd = Opd.of(
                "63.11",
                "5.01.5.05.0.00.01.0000",
                "BKPSDM",
                "BKPSDM"
        );

        when(opdRepository.existsByKodeOpd(opd.kodeOpd()))
                .thenReturn(true);

        assertThatThrownBy(() ->
                opdService.save(opd))
                .isInstanceOf(OpdAlreadyExistsException.class);
    }
}
