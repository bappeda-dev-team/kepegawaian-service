package cc.kertaskerja.kepegawaian.pegawai.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PegawaiRepositoryImpl implements PegawaiRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public PegawaiRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String UPSERT_SQL = """
            INSERT INTO pegawai (
                nip,
                nama_pegawai,
                jenis_kelamin,
                tempat_lahir,
                tanggal_lahir,
                jenis_pegawai,
                status_pegawai,
                keterangan
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT (nip) DO UPDATE SET
                nama_pegawai = EXCLUDED.nama_pegawai,
                jenis_kelamin = EXCLUDED.jenis_kelamin,
                tempat_lahir = EXCLUDED.tempat_lahir,
                tanggal_lahir = EXCLUDED.tanggal_lahir,
                jenis_pegawai = EXCLUDED.jenis_pegawai,
                status_pegawai = EXCLUDED.status_pegawai,
                keterangan = EXCLUDED.keterangan,
                last_modified_date = NOW()
            """;

    @Override
    public void upsertAll(List<Pegawai> pegawais) {
        if (pegawais.isEmpty()) {
            return;
        }

        jdbcTemplate.batchUpdate(
                UPSERT_SQL,
                pegawais,
                pegawais.size(),
                (ps, pegawai) -> {
                    ps.setString(1, pegawai.nip());
                    ps.setString(2, pegawai.namaPegawai());
                    ps.setString(3, pegawai.jenisKelamin());
                    ps.setString(4, pegawai.tempatLahir());
                    ps.setObject(5, pegawai.tanggalLahir());
                    ps.setString(6, pegawai.jenisPegawai());
                    ps.setString(7, pegawai.statusPegawai().name());
                    ps.setString(8, pegawai.keterangan());
                });
    }

    public List<Pegawai> findDistinctByJabatanPegawaiOpdId(Long opdId) {
        String script = """
                SELECT DISTINCT p.*
                FROM pegawai p
                JOIN jabatan_pegawai jp
                  ON jp.pegawai_id = p.id
                WHERE jp.opd_id = ?
                """;

        return jdbcTemplate.query(script, pegawaiRowMapper, opdId);
    }

    public List<Pegawai> findDistinctByJabatanPegawaiKodeOpd(String kodeOpd) {
        String script = """
                SELECT DISTINCT p.*
                FROM pegawai p
                JOIN jabatan_pegawai jp
                  ON jp.pegawai_id = p.id
                WHERE jp.kode_opd = ?
                """;

        return jdbcTemplate.query(script, pegawaiRowMapper, kodeOpd);
    }

    public List<Pegawai> findDistinctByJabatanPegawaiOpdIdAndRoleId(Long opdId, Long roleId) {
        String script = """
                SELECT DISTINCT p.*
                FROM pegawai p
                JOIN jabatan_pegawai jp
                  ON jp.pegawai_id = p.id
                JOIN role_pegawai rp
                  ON rp.pegawai_id = p.id
                WHERE jp.opd_id = ?
                AND rp.role_id = ?
                """;

        return jdbcTemplate.query(script, pegawaiRowMapper, opdId, roleId);
    }

    public List<Pegawai> findDistinctByJabatanPegawaiKodeOpdAndRoleId(String kodeOpd, Long roleId) {
        String script = """
                SELECT DISTINCT p.*
                FROM pegawai p
                JOIN jabatan_pegawai jp
                  ON jp.pegawai_id = p.id
                JOIN role_pegawai rp
                  ON rp.pegawai_id = p.id
                WHERE jp.kode_opd = ?
                AND rp.role_id = ?
                """;

        return jdbcTemplate.query(script, pegawaiRowMapper, kodeOpd, roleId);
    }

    public List<Pegawai> findDistinctByJabatanPegawaiOpdIdAndRolePegawaiRoleNama(Long opdId, String roleNama) {
        String script = """
                SELECT DISTINCT p.*
                FROM pegawai p
                JOIN jabatan_pegawai jp
                  ON jp.pegawai_id = p.id
                JOIN role_pegawai rp
                  ON rp.pegawai_id = p.id
                JOIN role r
                  ON r.id = rp.role_id
                WHERE jp.opd_id = ?
                AND r.nama_role = ?
                """;

        return jdbcTemplate.query(script, pegawaiRowMapper, opdId, roleNama);
    }

    @Override
    public Page<Pegawai> findAll(Pageable pageable) {

        String sql = """
                SELECT *
                FROM pegawai
                ORDER BY nama_pegawai ASC
                LIMIT ? OFFSET ?
                """;

        List<Pegawai> pegawai = jdbcTemplate.query(
                sql,
                pegawaiPaginationRowMapper,
                pageable.getPageSize(),
                pageable.getOffset());

        Long total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM pegawai",
                Long.class);

        return new PageImpl<>(
                pegawai,
                pageable,
                total != null ? total : 0);
    }

    @Override
    public Page<Pegawai> findAllFiltered(
            String kodeOpd,
            String search,
            Pageable pageable) {
        StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
        List<Object> params = new ArrayList<>();

        if (StringUtils.hasText(kodeOpd)) {
            where.append("""
                    AND EXISTS (
                        SELECT 1
                        FROM jabatan_pegawai jp
                        WHERE jp.pegawai_id = p.id
                          AND jp.kode_opd = ?
                    )
                    """);

            params.add(kodeOpd.strip());
        }

        if (StringUtils.hasText(search)) {
            where.append("""
                    AND (
                        p.nama_pegawai ILIKE ?
                        OR p.nip ILIKE ?
                    )
                    """);

            String keyword = "%" + search.strip() + "%";

            params.add(keyword);
            params.add(keyword);
        }

        String sql = """
                SELECT p.*
                FROM pegawai p
                """ + where + """
                ORDER BY p.nama_pegawai ASC, p.id ASC
                LIMIT ? OFFSET ?
                """;

        List<Object> queryParams = new ArrayList<>(params);
        queryParams.add(pageable.getPageSize());
        queryParams.add(pageable.getOffset());

        List<Pegawai> pegawai = jdbcTemplate.query(
                sql,
                pegawaiPaginationRowMapper,
                queryParams.toArray());

        String countSql = """
                SELECT COUNT(*)
                FROM pegawai p
                """ + where;

        Long total = jdbcTemplate.queryForObject(
                countSql,
                Long.class,
                params.toArray());

        return new PageImpl<>(
                pegawai,
                pageable,
                total != null ? total : 0);
    }

    private final RowMapper<Pegawai> pegawaiRowMapper = (rs, rowNum) -> new Pegawai(
            rs.getLong("id"),
            rs.getString("nip"),
            rs.getString("nama_pegawai"),
            rs.getString("jenis_kelamin"),
            rs.getString("tempat_lahir"),
            rs.getObject("tanggal_lahir", LocalDate.class),
            rs.getString("jenis_pegawai"),
            rs.getString("status_pegawai") != null
                    ? PegawaiStatus.valueOf(rs.getString("status_pegawai"))
                    : null,
            rs.getString("keterangan"),
            rs.getString("keycloak_user_id"),
            rs.getTimestamp("created_date") != null
                    ? rs.getTimestamp("created_date").toInstant()
                    : null,
            rs.getTimestamp("last_modified_date") != null
                    ? rs.getTimestamp("last_modified_date").toInstant()
                    : null);

    private final RowMapper<Pegawai> pegawaiPaginationRowMapper = (rs, rowNum) -> new Pegawai(
            rs.getLong("id"),
            rs.getString("nip"),
            rs.getString("nama_pegawai"),
            rs.getString("jenis_kelamin"),
            rs.getString("tempat_lahir"),
            rs.getObject("tanggal_lahir", LocalDate.class),
            rs.getString("jenis_pegawai"),
            rs.getString("status_pegawai") != null
                    ? PegawaiStatus.valueOf(rs.getString("status_pegawai"))
                    : null,
            rs.getString("keterangan"),
            rs.getString("keycloak_user_id"),
            rs.getTimestamp("created_date") != null
                    ? rs.getTimestamp("created_date").toInstant()
                    : null,
            rs.getTimestamp("last_modified_date") != null
                    ? rs.getTimestamp("last_modified_date").toInstant()
                    : null);
}
