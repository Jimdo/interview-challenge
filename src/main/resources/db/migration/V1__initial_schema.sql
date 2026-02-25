CREATE TABLE app_user
(
    id         UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version    INTEGER                     NOT NULL,
    email      VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id),
    CONSTRAINT uq_app_user_email UNIQUE (email)
);

CREATE TABLE website
(
    id         UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version    INTEGER                     NOT NULL,
    user_id    UUID                        NOT NULL,
    CONSTRAINT pk_website PRIMARY KEY (id),
    CONSTRAINT fk_website_user FOREIGN KEY (user_id) REFERENCES app_user (id)
);

CREATE TABLE website_version
(
    id             UUID                        NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version        INTEGER                     NOT NULL,
    website_id     UUID                        NOT NULL,
    version_number INTEGER                     NOT NULL,
    payload        TEXT                        NOT NULL,
    CONSTRAINT pk_website_version PRIMARY KEY (id),
    CONSTRAINT fk_website_version_website FOREIGN KEY (website_id) REFERENCES website (id),
    CONSTRAINT uq_website_version UNIQUE (website_id, version_number)
);

CREATE TABLE domain
(
    domain     VARCHAR(255)                NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    website_id UUID                        NOT NULL,
    CONSTRAINT pk_domain PRIMARY KEY (domain),
    CONSTRAINT fk_domain_website FOREIGN KEY (website_id) REFERENCES website (id)
);

CREATE INDEX idx_website_user_id ON website (user_id);
CREATE INDEX idx_website_version_website_id ON website_version (website_id);
CREATE INDEX idx_domain_website_id ON domain (website_id);
