package cc.kertaskerja.kepegawaian.jabatan_pegawai.web;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiService;
import cc.kertaskerja.kepegawaian.common.web.WebResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jabatan-pegawai")
@Tag(
        name = "Jabatan Pegawai",
        description = "Manajemen data jabatan dan opd pegawai"
)
public class JabatanPegawaiController {
    private final JabatanPegawaiService jabatanPegawaiService;

    public JabatanPegawaiController(JabatanPegawaiService jabatanPegawaiService) {
        this.jabatanPegawaiService = jabatanPegawaiService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Tambah jabatan pegawai",
            description = "Menetapkan jabatan utama pertama pegawai.",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Jabatan pegawai berhasil ditambahkan"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "request body tidak invalid"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "invalid auth"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pegawai / opd / master jabatan tidak ditemukan"
            )
    })
    public WebResponse<JabatanPegawaiResponse> tambahJabatanPegawai(
            @Valid
            @RequestBody
            JabatanPegawaiCreateRequest request
    ) {
        JabatanPegawaiResponse response = JabatanPegawaiResponse.from(
                jabatanPegawaiService.tambahJabatan(request.toCommand()
                ));

        return WebResponse.created("Jabatan pegawai berhasil ditambahkan", response);
    }

    @PostMapping("/non-aktifkan/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Non aktifkan jabatan pegawai",
            description = "Menonaktifkan jabatan pegawai terpilih.",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Jabatan dinonaktifkan"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "request body tidak invalid"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "invalid auth"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Jabatan Pegawai tidak ditemukan"
            )
    })
    public WebResponse<JabatanPegawaiResponse> nonAktifkanJabatanPegawai(
            @PathVariable Long id,
            @Valid @RequestBody JabatanPegawaiNonAktifkanRequest request
    ) {
        JabatanPegawaiResponse response = JabatanPegawaiResponse.from(
                jabatanPegawaiService.akhiriJabatan(id, request.alasanBerakhir(), request.tmtAkhir())
        );

        return WebResponse.success("Jabatan berhasil dinonaktifkan", response);
    }
}
