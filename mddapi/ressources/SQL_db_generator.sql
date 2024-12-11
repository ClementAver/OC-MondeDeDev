create table topics
(
    id          int auto_increment
        primary key,
    created_at  datetime(6)   null,
    description varchar(2000) not null,
    title       varchar(255)  not null,
    updated_at  datetime(6)   null
);

create table users
(
    id         int auto_increment
        primary key,
    created_at datetime(6)  null,
    email      varchar(255) not null,
    name       varchar(255) not null,
    password   varchar(255) not null,
    updated_at datetime(6)  null
);

create table posts
(
    id         int auto_increment
        primary key,
    content    varchar(8000) not null,
    created_at datetime(6)   null,
    title      varchar(250)  not null,
    updated_at datetime(6)   null,
    topic_id   int           null,
    user_id    int           null,
    constraint FK5lidm6cqbc7u4xhqpxm898qme
        foreign key (user_id) references users (id),
    constraint FKrfchr8dax0kfngvvkbteh5n7h
        foreign key (topic_id) references topics (id)
);

create table comments
(
    id         int auto_increment
        primary key,
    content    varchar(250) not null,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    post_id    int          null,
    user_id    int          null,
    constraint FK8omq0tc18jd43bu5tjh6jvraq
        foreign key (user_id) references users (id),
    constraint FKh4c7lvsc298whoyd4w9ta25cr
        foreign key (post_id) references posts (id)
);

create table subscriptions
(
    user_id  int not null,
    topic_id int not null,
    constraint FKfuag8et2vdg3ds9h8trqx2ldq
        foreign key (topic_id) references topics (id),
    constraint FKhro52ohfqfbay9774bev0qinr
        foreign key (user_id) references users (id)
);

