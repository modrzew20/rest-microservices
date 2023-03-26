create table change
(
    id                  uuid         not null,
    changed_by          varchar(255) not null,
    changed_date        timestamp(6) not null,
    parameter_name      varchar(255) not null,
    parameter_new_value float(53)    not null,
    parameter_old_value float(53)    not null,
    primary key (id)
);
create table role
(
    name varchar(255) not null,
    primary key (name)
);
create table shape
(
    dtype            varchar(31)  not null,
    id               uuid         not null,
    version          bigint,
    area             float(53)    not null,
    created_at       timestamp(6) not null,
    created_by       varchar(255) not null,
    last_modified_at timestamp(6) not null,
    last_modified_by varchar(255) not null,
    perimeter        float(53)    not null,
    type             varchar(255) not null,
    radius           float(53),
    length           float(53),
    width            float(53),
    primary key (id)
);
create table shape_changes
(
    shape_id   uuid not null,
    changes_id uuid not null
);
create table users
(
    id         uuid         not null,
    version    bigint,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    login      varchar(255) not null,
    password   varchar(255) not null,
    role_name  varchar(255),
    primary key (id)
);
create table users_figures
(
    users_id   uuid not null,
    figures_id uuid not null
);
alter table if exists shape_changes add constraint UK_qu61j144y9w1u9vx9ajx4aor9 unique (changes_id);
alter table if exists users add constraint UK_ow0gan20590jrb00upg3va2fn unique (login);
alter table if exists users_figures add constraint UK_bmme9qb3quf9jjlbfpm1qi2uh unique (figures_id);
alter table if exists shape_changes add constraint FKblojkq2i8f9lqqbxnru17s3ej foreign key (changes_id) references change;
alter table if exists shape_changes add constraint FK4y20fvh0orvw440e2dn6x696l foreign key (shape_id) references shape;
alter table if exists users add constraint FKio7xawwvpk025stk4xkq2t0y8 foreign key (role_name) references role;
alter table if exists users_figures add constraint FK1ln6vi2dxvpc9am2jhlurunso foreign key (figures_id) references shape;
alter table if exists users_figures add constraint FKt0ocyln9so9jre0eprxno6b25 foreign key (users_id) references users;
