CREATE TABLE pegawai (
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    nip                 VARCHAR(50) UNIQUE NOT NULL,
    nama_pegawai        VARCHAR(255) NOT NULL,
    status_pegawai      VARCHAR(20) NOT NULL DEFAULT('AKTIF'),
    created_date        TIMESTAMP NOT NULL DEFAULT(NOW()),
    last_modified_date  TIMESTAMP NOT NULL DEFAULT(NOW())
);

CREATE INDEX idx_pegawai_status_pegawai
    ON pegawai(status_pegawai);