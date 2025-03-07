CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(31) UNIQUE NOT NULL,
    password VARCHAR(127) NOT NULL,
    role VARCHAR(16) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS files (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    username VARCHAR(31) NOT NULL,
    filename VARCHAR(254) NOT NULL,
    bucket VARCHAR(31) NOT NULL,
    file_key VARCHAR(63) NOT NULL,
    content_type VARCHAR(31) NOT NULL,
    file_size BIGINT,
    created_at TIMESTAMP NOT NULL
)
