CREATE TABLE master_jabatan(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    opd_id          BIGINT NOT NULL,
    kode_jabatan    VARCHAR(255) NOT NULL,
    nama_jabatan    VARCHAR(255) NOT NULL,
    jenjang_jabatan VARCHAR(255) NOT NULL DEFAULT('BELUM_ADA_JENJANG'),
    status_jabatan  VARCHAR(50) NOT NULl DEFAULT('AKTIF'),
    created_date        TIMESTAMPTZ NOT NULL DEFAULT(NOW()),
    last_modified_date  TIMESTAMPTZ NOT NULL DEFAULT(NOW()),

    CONSTRAINT uk_master_jabatan_opd_kode UNIQUE (opd_id, kode_jabatan),
    CONSTRAINT fk_master_jabatan_opd FOREIGN KEY (opd_id) REFERENCES opd(id)
);

CREATE INDEX idx_master_jabatan_opd_id
    ON master_jabatan(opd_id);