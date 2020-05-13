create table users (
    id     uuid         primary key not null,
    login       varchar(255) unique      not null,
    password    varchar(255)             not null,
    email       varchar(255)             not null,
    first_name  varchar(128)             not null,
    family_name varchar(128)             not null,
    patronymic  varchar(128)
);

create table roles (
    id uuid        primary key not null,
    name    varchar(50) unique      not null
);

create table user_roles (
    user_id uuid not null references users(id),
    role_id uuid not null references roles(id)
);

create table season_services (
    id  uuid         primary key not null,
    name        varchar(255) unique      not null,
    start_date  timestamp                not null,
    end_date    timestamp                not null,
    usage_limit integer                  not null,
    used        integer                  not null
);

create table provided_services (
    id uuid primary key not null,
    service_id          uuid             not null references season_services(id),
    user_id             uuid             not null references users(id),
    creation_date       timestamp        not null,
    provision_date      timestamp,
    serial_number       integer          not null
);

delete from user_roles;
delete from provided_services;
delete from users;
delete from roles;
delete from season_services;