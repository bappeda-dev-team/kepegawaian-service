CREATE TABLE mapping_opd (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    sumber VARCHAR(50) NOT NULL,
    kode_sumber VARCHAR(100) NOT NULL,
    kode_master VARCHAR(100) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_mapping_opd_sumber_kode
        UNIQUE (sumber, kode_sumber),

    CONSTRAINT uq_mapping_opd_sumber_master
                                 UNIQUE (sumber, kode_master)
);

CREATE INDEX idx_mapping_opd_kode_master
    ON mapping_opd (kode_master);
