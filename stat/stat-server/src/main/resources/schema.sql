CREATE TABLE IF NOT EXISTS hits (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    app varchar(1024) NOT NULL,
    uri varchar(2083) NOT NULL,
    ip varchar(31) NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_hit PRIMARY KEY (id)
);
