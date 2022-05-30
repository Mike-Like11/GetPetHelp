create table users
(
    id       bigint auto_increment
        primary key,
    email    varchar(255) null,
    password varchar(255) null,
    role     int          null
);
create table full_user_info
(
    id          bigint auto_increment
        primary key,
    age         int          not null,
    city        varchar(255) null,
    middle_name varchar(255) null,
    telegram    bit          not null,
    viber       bit          not null,
    whats_app   bit          not null,
    user_id     bigint       not null,
    constraint FKbowucqslhv4ye3r52dvhycd4n
        foreign key (user_id) references users (id)
);
create table short_user_info
(
    id         bigint auto_increment
        primary key,
    avatar_url varchar(255) null,
    first_name varchar(255) null,
    last_name  varchar(255) null,
    phone      varchar(255) null,
    user_id    bigint       not null,
    constraint FKamcbrv3a62ymyqn6qta4p42qk
        foreign key (user_id) references users (id)
);
create table worker
(
    id      bigint not null
        primary key,
    rating  double null,
    user_id bigint not null,
    constraint FKmepbqcmfqsja2564rd2v52py9
        foreign key (user_id) references short_user_info (id)
);
create table worker_info
(
    id          bigint       not null
        primary key,
    experience  varchar(255) null,
    preferences varchar(255) null,
    worker_id   bigint       null,
    constraint FKbq3slersds3ou0252veskmo41
        foreign key (worker_id) references worker (id)
);
create table task
(
    id               bigint       not null
        primary key,
    date_of_creation varchar(255) null,
    user_id          bigint       not null,
    worker_id        bigint       null,
    constraint FK1lmxm4hd92kjj2kt6inbs6cjr
        foreign key (user_id) references short_user_info (id),
    constraint FKqn9llutcpc2bbicat33bh9jrp
        foreign key (worker_id) references worker (id)
);
create table task_info
(
    id                  bigint       not null
        primary key,
    date_of_performance varchar(255) null,
    description         varchar(255) null,
    latitude            double       null,
    longitude           double       null,
    name                varchar(255) null,
    task_id             bigint       null,
    constraint FKer9mcdyqf63005umklqiv1kia
        foreign key (task_id) references task (id)
);
create table task_worker_info_list
(
    task_id             bigint not null,
    worker_info_list_id bigint not null,
    constraint FK3j6873u3vsoga8kaux5930tlf
        foreign key (task_id) references task (id),
    constraint FKg0hfmmf6bc7vj7dai4m02clpt
        foreign key (worker_info_list_id) references worker (id)
);
create table review
(
    id        bigint       not null
        primary key,
    date      varchar(255) null,
    message   varchar(255) null,
    rating    double       null,
    user_id   bigint       not null,
    worker_id bigint       null,
    constraint FKc33pi8p8m5tgdsxrqg0u1jpk9
        foreign key (worker_id) references worker (id),
    constraint FKs0v1v3ccs6ka3ek4x1fw020s
        foreign key (user_id) references short_user_info (id)
);
insert into users (id, email, password, role)
values (1, 'mike7677877@gmail.com', '$2a$10$QL4jHIvxD6HFqG9UqHKFrOZqS.M19CCrZh.6VYmJo7ze7qO1PmjCC', 0);
insert into short_user_info (id, avatar_url, first_name, last_name, phone, user_id)
values (1, 'https://res.cloudinary.com/db5k4vzwg/image/upload/v1653664061/jcifuxlpfeevypsvm0ko.jpg', 'Mike', 'I', '89103634532',1);
insert into full_user_info (id, age, city, middle_name, telegram, viber, whats_app, user_id)
values (1, 20, 'Moscow', 'Nikolaevich', true, true, true, 1);
insert into users (id, email, password, role)
values (2, 'nikola@yandex.ru', '$2a$10$QL4jHIvxD6HFqG9UqHKFrOZqS.M19CCrZh.6VYmJo7ze7qO1PmjCC', 0);
insert into short_user_info (id, avatar_url, first_name, last_name, phone, user_id)
values (2, 'https://res.cloudinary.com/db5k4vzwg/image/upload/v1653658790/ymrw4knclkcwqpljpazo.jpg', 'Nikola', 'Messi', '89103634532',2);
insert into full_user_info (id, age, city, middle_name, telegram, viber, whats_app, user_id)
values (2, 20, 'moscow', 'Alekseevich', true, true, true, 2);
insert into users (id, email, password, role)
values (3, 'best-user@mail.ru', '$2a$10$QL4jHIvxD6HFqG9UqHKFrOZqS.M19CCrZh.6VYmJo7ze7qO1PmjCC', 0);
insert into short_user_info (id, avatar_url, first_name, last_name, phone, user_id)
values (3, 'https://res.cloudinary.com/db5k4vzwg/image/upload/v1651656289/cld-sample.jpg', 'Elena', 'Sh', '89103634532',3);
insert into full_user_info (id, age, city, middle_name, telegram, viber, whats_app, user_id)
values (3, 20, 'Volgograd', 'Anatolievna', true, true, true, 3);
insert into worker (id, rating, user_id)
values (1,5.0,1),
       (2,3.0,2),
       (3,4.0,3);
insert into worker_info (id, experience, preferences, worker_id)
values (1,'Работаю в данной сфере более 5 лет','',1),
       (2,'Работаю в данной сфере более 3 лет','',2),
       (3,'Работаю в данной сфере более 10 лет','',3);
insert into task (id, date_of_creation, user_id, worker_id)
values (1,'May 30, 2022, 1:21:02 PM',1,null),
       (2,'May 30, 2022, 1:21:02 PMэ',2,null),
       (3,'May 30, 2022, 1:21:02 PM', 3, null);
insert into task_info (id, date_of_performance, description, latitude, longitude, name, task_id)
values (1,'Воскресенье, 5 Июня 2022 10:00:00','Необходимо погулять с собакой в течение двух часов, зовут  Михалыч', 57.383418, 47.011181, 'Прогулка с собакой' ,1),
       (2,'Вторник, 31 мая 2022 18:00:00','Необходимо купить корм для собаки и привезти по указанному адресу', 55.735771, 37.585353, 'Покупка корма' ,2),
       (3, 'Четверг, 2 июня 2022 21:00:00','Необходимо посидеть с кошкой 3 часа по указанному адресу', 48.657589, 44.467079, 'Посидеть с кошкой дома' ,3);