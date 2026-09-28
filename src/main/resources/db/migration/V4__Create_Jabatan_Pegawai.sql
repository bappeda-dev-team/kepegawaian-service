CREATE TABLE jabatan_pegawai (
    id                      BIGSERIAL PRIMARY KEY NOT NULL,
    pegawai_id              BIGINT  NOT NULL,
    master_jabatan_id       BIGINT  NOT NULL,
    nama_jabatan            VARCHAR(255) NOT NULL,

    opd_id                  BIGINT  NOT NULL,
    kode_opd                VARCHAR(255) NOT NULL,
    nama_opd                VARCHAR(255) NOT NULL,

    jenis_penugasan         VARCHAR(255) NOT NULL DEFAULT('BELUM_DIATUR'),
    alasan_berakhir         VARCHAR(255),

    tmt_mulai               DATE NOT NULL,
    tmt_akhir               DATE,

    created_date            TIMESTAMP NOT NULL DEFAULT(NOW()),
    last_modified_date      TIMESTAMP NOT NULL DEFAULT(NOW())
);

CREATE INDEX idx_jabatan_pegawai_pegawai_id
    ON jabatan_pegawai(pegawai_id);

CREATE INDEX idx_jabatan_pegawai_master_jabatan_id
    ON jabatan_pegawai(master_jabatan_id);

CREATE INDEX idx_jabatan_pegawai_opd_id
    ON jabatan_pegawai(opd_id);

CREATE INDEX idx_jabatan_pegawai_aktif
    ON jabatan_pegawai(pegawai_id)
    WHERE tmt_akhir IS NULL;