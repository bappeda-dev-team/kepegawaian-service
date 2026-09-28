CREATE TABLE role_pegawai (
    id                      BIGSERIAL PRIMARY KEY NOT NULL,
    pegawai_id              BIGINT  NOT NULL,
    role_id                 BIGINT  NOT NULL,

    created_date            TIMESTAMP NOT NULL DEFAULT(NOW()),
    last_modified_date      TIMESTAMP NOT NULL DEFAULT(NOW()),

    CONSTRAINT uk_role_pegawai
        UNIQUE (pegawai_id, role_id)
);

CREATE INDEX idx_role_pegawai_role_id
    ON role_pegawai(role_id);