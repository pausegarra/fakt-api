create table invoice_lines
(
    quantity    float(53)      not null,
    total       numeric(38, 2) not null,
    unit_price  numeric(38, 2) not null,
    created_at  timestamp(6) with time zone,
    updated_at  timestamp(6) with time zone,
    id          uuid           not null,
    invoice_id  uuid           not null,
    line_number varchar(255)   not null,
    primary key (id)
);
create table invoices
(
    date           date,
    created_at     timestamp(6) with time zone,
    updated_at     timestamp(6) with time zone,
    customer_id    uuid         not null,
    id             uuid         not null,
    invoice_number varchar(255) not null unique,
    primary key (id)
);
alter table if exists invoice_lines
    add constraint FKsgudq2lwpa9wc92a23nggah1w foreign key (invoice_id) references invoices;
alter table if exists invoices
    add constraint FKq2w4hmh6l9othnp6cepp0cfe2 foreign key (customer_id) references customers;