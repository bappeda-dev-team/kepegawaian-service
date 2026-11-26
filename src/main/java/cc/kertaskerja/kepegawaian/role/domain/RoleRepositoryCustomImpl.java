package cc.kertaskerja.kepegawaian.role.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {
    private final JdbcTemplate jdbcTemplate;

    public RoleRepositoryCustomImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        String sql = """
                SELECT *
                FROM role
                ORDER BY id ASC
                LIMIT ? OFFSET ?
                """;

        List<Role> roles = jdbcTemplate.query(
                sql,
                rolePaginationRowMapper,
                pageable.getPageSize(),
                pageable.getOffset());

        Long total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM role",
                Long.class);

        return new PageImpl<>(
                roles,
                pageable,
                total != null ? total : 0);
    }

    private final RowMapper<Role> rolePaginationRowMapper = (rs, rowNum) -> new Role(
            rs.getObject("id", Long.class),
            rs.getString("kode_role"),
            rs.getString("nama_role"),
            rs.getBoolean("is_admin"),
            rs.getTimestamp("created_date") != null
                    ? rs.getTimestamp("created_date").toInstant()
                    : null,
            rs.getTimestamp("last_modified_date") != null
                    ? rs.getTimestamp("last_modified_date").toInstant()
                    : null);
}
