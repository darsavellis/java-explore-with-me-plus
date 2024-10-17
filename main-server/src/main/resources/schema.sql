CREATE TABLE IF NOT EXISTS users (
    id BIGINT,
    name varchar(250),
    email varchar(254)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT,
    name varchar(50)
);

CREATE TABLE IF NOT EXISTS events (
    id BIGINT,
    annotations varchar(2000),
    category_id BIGINT,
    confirmed_requests INT,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    description varchar(7000),
    event_date TIMESTAMP WITHOUT TIME ZONE,
    initiator_id BIGINT,
    location_id BIGINT,
    paid BOOLEAN,
    participant_limit INT,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state VARCHAR(10),
    title VARCHAR(120),
    views BIGINT
);

CREATE TABLE IF NOT EXISTS locations (
    id BIGINT,
    latitude FLOAT,
    longitude FLOAT
);
