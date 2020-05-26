create table cities
(
    idcities   int         not null
        primary key,
    citiy_name varchar(45) not null,
    province   varchar(45) not null,
    constraint index2
        unique (citiy_name, province)
);

create table stations
(
    code_name    varchar(45) null,
    simp_name    varchar(45) null,
    ping_ying    varchar(45) null,
    id           varchar(45) not null
        primary key,
    py_code      varchar(45) null,
    chinese_name varchar(45) not null,
    constraint chinese_name_UNIQUE
        unique (chinese_name)
);

create table trains
(
    code           varchar(45) not null
        primary key,
    train_name     varchar(45) not null,
    depart_time    varchar(45) null,
    depart_station varchar(45) not null,
    arrive_station varchar(45) not null,
    constraint index2
        unique (train_name, depart_time)
);

create table tickets
(
    tickets_price  double      null,
    seat_type      varchar(45) not null,
    train          varchar(45) not null,
    rest_tickets   int         not null,
    depart_time    datetime    null,
    depart_station varchar(45) not null,
    arrive_station varchar(45) not null,
    id             int auto_increment
        primary key,
    tickets_orderd int         not null,
    constraint index2
        unique (train, seat_type, depart_station, arrive_station),
    constraint fk_tickets_1
        foreign key (train) references trains (code)
);

create table trains_stations
(
    train        varchar(45) not null,
    depart_time  datetime    not null,
    arrive_time  datetime    not null,
    station_name varchar(45) not null,
    id           int auto_increment
        primary key,
    distance     int         not null,
    stay_time    varchar(45) null,
    number_days  int         null,
    drive_time   varchar(45) null,
    constraint index4
        unique (station_name, train),
    constraint fk_routes_stations_2
        foreign key (train) references trains (code)
);

create index fk_routes_stations_2_idx
    on trains_stations (train);

create table users
(
    ID_card_num  char(17)             not null
        primary key,
    usersname    varchar(45)          null,
    phone_number char(11)             null,
    admin        tinyint(1) default 0 null,
    pass_word    varchar(45)          not null
);

create table orders
(
    idorders      int auto_increment
        primary key,
    create_date   datetime    not null,
    orders_status varchar(45) not null,
    orders_price  double      null,
    user          char(17)    not null,
    tickets_id    int         not null,
    constraint index2
        unique (user, create_date),
    constraint fk_orders_1
        foreign key (user) references users (ID_card_num),
    constraint fk_orders_2
        foreign key (tickets_id) references tickets (id)
);

create index fk_orders_2_idx
    on orders (tickets_id);

