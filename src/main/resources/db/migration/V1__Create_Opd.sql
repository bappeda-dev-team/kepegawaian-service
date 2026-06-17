CREATE TABLE opd (
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    kode_lembaga        VARCHAR(5) NOT NULL,
    kode_opd            VARCHAR(22) UNIQUE NOT NULL,
    nama_opd            VARCHAR(255) NOT NULL,
    singkatan_opd       VARCHAR(30) NOT NULL,
    status_opd          VARCHAR(50) NOT NULl DEFAULT('AKTIF'),
    created_date        TIMESTAMP NOT NULL DEFAULT(NOW()),
    last_modified_date  TIMESTAMP NOT NULL DEFAULT(NOW())
);
