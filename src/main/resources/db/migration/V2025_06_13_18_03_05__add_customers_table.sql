create table customers
(
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    id         uuid not null,
    address    varchar(255),
    city       varchar(255),
    country    varchar(255),
    county     varchar(255),
    email      varchar(255) unique,
    name       varchar(255),
    nif        varchar(255) unique,
    postcode   varchar(255),
    primary key (id)
);
