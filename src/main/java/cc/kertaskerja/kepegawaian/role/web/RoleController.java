package cc.kertaskerja.kepegawaian.role.web;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/role")
@Tag(
        name = "Role",
        description = "Master data Role"
)
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @Operation(
        summary = "Daftar role",
        description = "Daftar role yang ada"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Daftar Role berhasil diambil",
            content = @Content(
                array = @ArraySchema(
                    schema = @Schema(implementation = RoleResponse.class)
                )
            )
        )
    })
    public WebResponse<List<RoleResponse>> findAll() {
        List<RoleResponse> responses = roleService.findAll()
            .stream()
            .map(RoleResponse::from)
            .toList();

        return WebResponse.success(responses);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Role by id",
        description = "Cari role berdasarkan id"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Role ditemukan",
            content = @Content(
                schema = @Schema(implementation = RoleResponse.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "ID role tidak valid"),
        @ApiResponse(responseCode = "404", description = "Role tidak ditemukan")
    })
    public WebResponse<RoleResponse> findById(
        @Parameter(
            description = "id role",
            example = "1",
            required = true
        )
        @PathVariable Long id
    ) {
        return WebResponse.success(
            RoleResponse.from(roleService.findRoleById(id))
        );
    }

    @PostMapping
    @Operation(
            summary = "Tambah role baru",
            description = "Menambahkan data role baru"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Role berhasil ditambahkan"),
            @ApiResponse(responseCode = "400", description = "Data tidak valid"),
            @ApiResponse(responseCode = "409", description = "Role sudah ada")
    })
    public WebResponse<RoleResponse> create(
            @Valid @RequestBody RoleCreateRequest request
    ) {
        RoleReponse response = RoleResponse.from(roleService.create(
                request.toCommand()
        ));

        return WebResponse.created(
                "Role berhasil ditambahkan",
                response
        );
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Update role",
            description = "Update nama role"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role berhasil diupdate"),
            @ApiResponse(responseCode = "400", description = "Data tidak valid"),
            @ApiResponse(responseCode = "409", description = "Role sudah ada")
    })
    public WebResponse<RoleResponse> create(
            @Valid @RequestBody RoleUpdateRequest request,
            @PathVariable Long id
    ) {
        RoleReponse response = RoleResponse.from(
            roleService.update(id, request.toCommand())
        );

        return WebResponse.created(
                "Role berhasil diperbarui",
                response
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Hapus role",
        description = "Hapus data role, by role id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Role berhasil dihapus"),
        @ApiResponse(responseCode = "404", description = "Role tidak ditemukan")
    })
    public WebResponse<Void> delete(
        @PathVariable Long id
    ) {
        String namaRole = roleService.delete(id);

        return WebResponse.deleted(
                "Role " + namaRole + " berhasil dihapus"
        );
    }
}
