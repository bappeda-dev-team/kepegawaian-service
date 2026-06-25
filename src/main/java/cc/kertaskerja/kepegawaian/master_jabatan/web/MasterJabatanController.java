package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanService;
import cc.kertaskerja.kepegawaian.common.web.EnumLabelResponse;
import cc.kertaskerja.kepegawaian.common.web.WebResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master-jabatan")
@Tag(
        name = "Master Jabatan",
        description = "Manajemen master data jabatan pemerintah daerah"
)
public class MasterJabatanController {

    private final MasterJabatanService masterJabatanService;

    public MasterJabatanController(MasterJabatanService masterJabatanService) {
        this.masterJabatanService = masterJabatanService;
    }

    @GetMapping
    @Operation(
            summary = "Daftar seluruh jabatan",
            description = "Mengambil seluruh data master jabatan"
    )
    public WebResponse<List<MasterJabatanResponse>> findAll() {
        List<MasterJabatanResponse> responses = masterJabatanService.findAll()
                .stream()
                .map(MasterJabatanResponse::from)
                .toList();

        return WebResponse.success(responses);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Detail jabatan",
            description = "Mengambil detail master jabatan berdasarkan ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Data ditemukan"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Data tidak ditemukan",
            content = @Content
    )
    public WebResponse<MasterJabatanResponse> findById(
            @Parameter(description = "ID master jabatan", example = "1")
            @PathVariable Long id
    ) {
        MasterJabatanResponse response = MasterJabatanResponse.from(
                masterJabatanService.findMasterJabatanById(id)
        );

        return WebResponse.success(response);
    }

    @GetMapping("/options/jenjang")
    @Operation(
            summary = "Daftar jenjang jabatan",
            description = "Mengambil daftar jenjang jabatan untuk kebutuhan pilihan"
    )
    public WebResponse<List<EnumLabelResponse>> listJenjang() {
        List<EnumLabelResponse> responses = masterJabatanService.listJenjangJabatan()
                .stream()
                .map(EnumLabelResponse::of)
                .toList();

        return WebResponse.success(responses);
    }

    @GetMapping("/options/kategori")
    @Operation(
            summary = "Daftar kategori jabatan",
            description = "Mengambil daftar kategori jabatan untuk kebutuhan pilihan"
    )
    public WebResponse<List<EnumLabelResponse>> listKategori() {
        List<EnumLabelResponse> responses = masterJabatanService.listKategoriJabatan()
                .stream()
                .map(EnumLabelResponse::of)
                .toList();

        return WebResponse.success(responses);
    }

    @GetMapping("/options/status")
    @Operation(
            summary = "Daftar status jabatan",
            description = "Mengambil daftar status jabatan untuk kebutuhan pilihan"
    )
    public WebResponse<List<EnumLabelResponse>> listStatus() {
        List<EnumLabelResponse> responses = masterJabatanService.listStatusJabatan()
                .stream()
                .map(EnumLabelResponse::of)
                .toList();

        return WebResponse.success(responses);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Tambah jabatan",
            description = "Menambahkan data master jabatan baru"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Data berhasil dibuat"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Validasi gagal",
            content = @Content
    )
    public WebResponse<MasterJabatanResponse> create(
            @Valid @RequestBody MasterJabatanCreateRequest request
    ) {
        MasterJabatanResponse response = MasterJabatanResponse.from(
                masterJabatanService.create(request.toCommand())
        );

        return WebResponse.created(
                "Jabatan berhasil ditambahkan",
                response
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Perbarui jabatan",
            description = "Memperbarui data master jabatan"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Data berhasil diperbarui"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Data tidak ditemukan",
            content = @Content
    )
    public WebResponse<MasterJabatanResponse> update(
            @Parameter(description = "ID master jabatan", example = "1")
            @PathVariable Long id,

            @Valid @RequestBody MasterJabatanUpdateRequest request
    ) {
        MasterJabatanResponse response = MasterJabatanResponse.from(
                masterJabatanService.update(id, request.toCommand())
        );

        return WebResponse.success(
                "Jabatan berhasil diperbarui",
                response
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Hapus jabatan",
            description = "Menghapus data master jabatan"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Data berhasil dihapus"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Data tidak ditemukan",
            content = @Content
    )
    public WebResponse<Void> delete(
            @Parameter(description = "ID master jabatan", example = "1")
            @PathVariable Long id
    ) {
        String namaJabatan = masterJabatanService.delete(id);

        return WebResponse.deleted(
                "Jabatan " + namaJabatan + " berhasil dihapus"
        );
    }
}
