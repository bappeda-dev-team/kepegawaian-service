package cc.kertaskerja.kepegawaian.opd;

import cc.kertaskerja.kepegawaian.opd.domain.Opd;
import cc.kertaskerja.kepegawaian.opd.domain.OpdService;
import cc.kertaskerja.kepegawaian.opd.web.OpdController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(OpdController.class)
class OpdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OpdService opdService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldReturnOpdList() throws Exception {

        when(opdService.findAllOpdAktifInLembaga())
                .thenReturn(List.of(
                        Opd.of(
                                "63.11",
                                "5.01.5.05.0.00.01.0000",
                                "BKPSDM",
                                "BKPSDM"
                        )
                ));

        mockMvc.perform(get("/opd/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].kode_opd")
                        .value("5.01.5.05.0.00.01.0000"));
    }
}
