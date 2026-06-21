CREATE TABLE IF NOT EXISTS auth_users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_auth_users_email ON auth_users (email);

CREATE TABLE IF NOT EXISTS auth_user_roles (
    user_id UUID NOT NULL,
    role VARCHAR(255) NOT NULL,
    CONSTRAINT fk_auth_user_roles_user
        FOREIGN KEY (user_id) REFERENCES auth_users (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS ix_auth_user_roles_user_id ON auth_user_roles (user_id);
