package cc.kertaskerja.kepegawaian.opd.web;

import cc.kertaskerja.kepegawaian.opd.domain.Opd;
import cc.kertaskerja.kepegawaian.opd.domain.OpdService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/opd")
@Tag(
        name = "OPD",
        description = "Manajemen Organisasi Perangkat Daerah"
)
public class OpdController {

    private final OpdService opdService;

    public OpdController(OpdService opdService) {
        this.opdService = opdService;
    }

    @GetMapping("/all")
    @Operation(
            summary = "Daftar OPD aktif",
            description = "Mengambil seluruh OPD aktif berdasarkan kode lembaga yang dikonfigurasi.",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Daftar OPD berhasil diambil",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = OpdResponse.class)
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Token tidak valid"),
            @ApiResponse(responseCode = "403", description = "Akses ditolak")
    })
    public List<OpdResponse> findAll() {
        return opdService.findAllOpdAktifInLembaga();
    }

    @GetMapping("/{kodeOpd}")
    @Operation(
            summary = "Detail OPD",
            description = "Mengambil detail OPD berdasarkan kode OPD.",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Detail OPD berhasil ditemukan",
                    content = @Content(
                            schema = @Schema(implementation = Opd.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "OPD tidak ditemukan")
    })
    public Opd findByKodeOpd(
            @Parameter(
                    description = "Kode OPD",
                    example = "5.01.5.05.0.00.01.0000"
            )
            @PathVariable String kodeOpd
    ) {
        return opdService.findOpdByKodeOpd(kodeOpd);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Membuat OPD",
            description = "Membuat data OPD baru.",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OPD berhasil dibuat"),
            @ApiResponse(responseCode = "400", description = "Data tidak valid"),
            @ApiResponse(responseCode = "409", description = "Kode OPD sudah terdaftar")
    })
    public Opd create(
            @Valid @RequestBody Opd opd
    ) {
        return opdService.save(opd);
    }

    @PutMapping("/{kodeOpd}")
    @Operation(
            summary = "Memperbarui OPD",
            description = "Memperbarui data OPD berdasarkan kode OPD.",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OPD berhasil diperbarui"),
            @ApiResponse(responseCode = "400", description = "Data tidak valid")
    })
    public Opd update(
            @PathVariable String kodeOpd,
            @Valid @RequestBody Opd opd
    ) {
        if (!kodeOpd.equals(opd.kodeOpd())) {
            throw new IllegalArgumentException(
                    "Kode OPD pada path dan body harus sama"
            );
        }

        return opdService.updateOrCreate(opd);
    }

    @DeleteMapping("/{kodeOpd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Menghapus OPD",
            description = "Menghapus data OPD berdasarkan kode OPD.",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "OPD berhasil dihapus"),
            @ApiResponse(responseCode = "404", description = "OPD tidak ditemukan")
    })
    public void delete(
            @PathVariable String kodeOpd
    ) {
        opdService.delete(kodeOpd);
    }
}
