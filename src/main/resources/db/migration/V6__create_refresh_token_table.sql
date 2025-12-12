CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE refresh_tokens (
    token UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,

    user_id BIGINT NOT NULL,

    -- Controle de revogação
    revoked BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key ligando ao usuário
    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE -- Se o user for deletado, apaga os tokens
);