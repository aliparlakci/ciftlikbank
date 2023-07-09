CREATE SCHEMA IF NOT EXISTS ciftlikbank;

create table ciftlikbank.account
(
    id          bigserial                not null
        constraint account_pkey
            primary key,
    created_at  timestamp with time zone not null,
    updated_at  timestamp with time zone,
    uid         varchar                  not null,
    owner_email varchar                  not null,
    currency    integer                  not null
);

create index account_uid_index on ciftlikbank.account (uid);

------------------------------------------------------------

create table ciftlikbank.account_snapshot
(
    id         bigserial                not null
        constraint account_snapshot_pkey
            primary key,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone,
    account_id bigint                   not null
        constraint fk_account_account_snapshot
            references ciftlikbank.account,
    balance    numeric                  not null
);

create index account_snapshot_account_id_index
    on ciftlikbank.account_snapshot (account_id);

-----------------------------------------------------------

create table ciftlikbank.exchange
(
    id                  bigserial                not null
        constraint exchange_pkey
            primary key,
    created_at          timestamp with time zone not null,
    updated_at          timestamp with time zone,
    uid                 varchar                  not null,
    deposit_account_id  bigint                   not null
        constraint fk_deposit_account_exchange
            references ciftlikbank.account,
    withdraw_account_id bigint                   not null
        constraint fk_withdraw_account_exchange
            references ciftlikbank.account,
    exchange_type       varchar                  not null,
    amount              numeric                  not null,
    rate                numeric                  not null
);

------------------------------------------------------------

create table ciftlikbank.transaction
(
    id               bigserial                not null
        constraint transaction_pkey
            primary key,
    created_at       timestamp with time zone not null,
    updated_at       timestamp with time zone,
    uid              varchar                  not null,
    account_id       bigint                   not null
        constraint fk_account_transaction
            references ciftlikbank.account,
    amount           numeric                  not null,
    reference_uid    varchar
);