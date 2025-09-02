-- ==== car table ====
CREATE TABLE IF NOT EXISTS car (
    id           UUID PRIMARY KEY,
    crn          VARCHAR(255) NOT NULL UNIQUE,
    brand        VARCHAR(255),
    model        VARCHAR(255),
    release_year INTEGER,
    rent_price   DOUBLE PRECISION NOT NULL
);

-- ==== customer table ====
CREATE TABLE IF NOT EXISTS customer (
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- ==== lease table ====
CREATE TABLE IF NOT EXISTS lease (
    id                  UUID PRIMARY KEY,
    car_id              UUID NOT NULL REFERENCES car(id) ON DELETE RESTRICT,
    customer_id         UUID NOT NULL REFERENCES customer(id) ON DELETE RESTRICT,
    start_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expected_return_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    returned_at         TIMESTAMP WITHOUT TIME ZONE,
    status              VARCHAR(255) NOT NULL
);