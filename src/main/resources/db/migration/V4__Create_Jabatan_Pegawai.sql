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
