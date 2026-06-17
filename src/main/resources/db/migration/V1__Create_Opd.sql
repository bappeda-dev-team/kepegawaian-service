CREATE TABLE opd (
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    kode_lembaga        VARCHAR(5) NOT NULL,
    kode_opd            VARCHAR(22) NOT NULL,
    nama_opd            VARCHAR(255) NOT NULL,
    singkatan_opd       VARCHAR(30) NOT NULL,
    status_opd          VARCHAR(50) NOT NULl DEFAULT('AKTIF'),
    created_date        TIMESTAMPTZ NOT NULL DEFAULT(NOW()),
    last_modified_date  TIMESTAMPTZ NOT NULL DEFAULT(NOW()),

    CONSTRAINT uk_opd_kode_opd UNIQUE (kode_opd)
);
