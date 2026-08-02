CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    kode_role VARCHAR(255) NOT NULL,
    nama_role VARCHAR(255) NOT NULL,

    created_date        TIMESTAMPTZ NOT NULL DEFAULT(NOW()),
    last_modified_date  TIMESTAMPTZ NOT NULL DEFAULT(NOW()),

    CONSTRAINT uk_role_kode_role UNIQUE (kode_role)
)
