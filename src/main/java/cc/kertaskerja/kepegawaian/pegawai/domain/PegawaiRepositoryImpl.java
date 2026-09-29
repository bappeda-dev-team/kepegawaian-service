package cc.kertaskerja.kepegawaian.pegawai.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
                }
        );
    }
}
