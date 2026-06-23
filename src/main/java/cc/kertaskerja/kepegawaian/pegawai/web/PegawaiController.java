package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiService;
import cc.kertaskerja.kepegawaian.web.WebResponse;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pegawai")
@Tag(
        name = "Pegawai",
        description = "Manajemen data pegawai"
)
public class PegawaiController {

    private final PegawaiService pegawaiService;

    public PegawaiController(PegawaiService pegawaiService) {
        this.pegawaiService = pegawaiService;
    }

    @GetMapping
    @Operation(
            summary = "Daftar Pegawai",
            description = "Mengambil seluruh data pegawai",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Daftar Pegawai berhasil diambil",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = PegawaiResponse.class)
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Token tidak valid"),
    })
    public WebResponse<List<PegawaiResponse>> findAll() {
        List<PegawaiResponse> responses = pegawaiService.findAll()
                .stream()
                .map(PegawaiResponse::from)
                .toList();

        return WebResponse.success(responses);
    }

    @GetMapping("/histori/{pegawaiId}")
    @Operation(
            summary = "Histori lengkap pegawai",
            description = "Menampilkan data histori pegawai",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Data Histori pegawai berhasil diambil",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = PegawaiHistoriResponse.class)
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Token tidak valid"),
            @ApiResponse(responseCode = "404", description = "Pegawai tidak ditemukan"),
    })
    public WebResponse<List<PegawaiHistoriResponse>> findHistoriPegawai(
            @Parameter(
                    description = "Pegawai id (nip)",
                    example = "1"
            )
            @PathVariable Long pegawaiId,

            @Valid @ParameterObject PegawaiHistoriQuery query
    ) {
        List<PegawaiHistoriResponse> response =
                pegawaiService.findHistoriPegawai(pegawaiId, query.jenisHistori(), query.bulan(), query.tahun())
                        .stream()
                        .map(PegawaiHistoriResponse::from)
                        .toList();

        return WebResponse.success(response);
    }

    @PostMapping
    @Operation(
            summary = "Tambah data pegawai",
            description = "Menambahkan data pegawai baru",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pegawai berhasil ditambahkan"),
            @ApiResponse(responseCode = "400", description = "Data tidak valid"),
            @ApiResponse(responseCode = "401", description = "Token tidak valid"),
            @ApiResponse(responseCode = "409", description = "Pegawai sudah ada")
    })
    public WebResponse<PegawaiResponse> create(
            @Valid @RequestBody PegawaiCreateRequest request
    ) {
        PegawaiResponse response = PegawaiResponse.from(pegawaiService.create(
                request.toCommand()
        ));

        return WebResponse.created(
                "Pegawai berhasil ditambahkan",
                response
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update data pegawai",
            description = "Update nama atau id pegawai",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Data pegawai berhasil diperbarui"),
            @ApiResponse(responseCode = "400", description = "Data tidak valid"),
            @ApiResponse(responseCode = "401", description = "Token tidak valid"),
            @ApiResponse(responseCode = "404", description = "Data pegawai tidak ditemukan")
    })
    public WebResponse<PegawaiResponse> update(
            @Valid @RequestBody PegawaiUpdateRequest request,
            @PathVariable Long id
    ) {
        PegawaiResponse response = PegawaiResponse.from(
                pegawaiService.update(id, request.toCommand()
        ));

        return WebResponse.success(
                "Data pegawai berhasil diperbarui",
                response
        );
    }

    @DeleteMapping("/id")
    @Operation(
            summary = "Hapus data pegawai",
            description = "hapus data pegawai by id",
            security = @SecurityRequirement(name = "sessionId")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Data pegawai berhasil diperbarui"),
            @ApiResponse(responseCode = "401", description = "Token tidak valid"),
            @ApiResponse(responseCode = "404", description = "Data pegawai tidak ditemukan")
    })
    public WebResponse<Void> delete(
            @PathVariable Long id
    ) {
        String namaPegawai = pegawaiService.delete(id);

        return WebResponse.deleted(
                "Pegawai " + namaPegawai + " berhasil dihapus"
        );
    }
}
