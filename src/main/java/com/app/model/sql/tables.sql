create table users (
    user_id     uuid         primary key not null,
    login       varchar(255) unique      not null,
    password    varchar(255)             not null,
    email       varchar(255)             not null,
    first_name  varchar(128)             not null,
    family_name varchar(128)             not null,
    patronymic  varchar(128)
);

create table roles (
    role_id uuid         primary key not null,
    name    varchar(255) unique      not null
);

create table user_roles (
    user_id uuid not null references users(user_id),
    role_id uuid not null references roles(role_id)
);