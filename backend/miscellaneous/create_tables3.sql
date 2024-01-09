-- entity
create table zones
(
    name varchar(30) primary key
);

-- entity
create table buildings
(
    building_id int primary key,
    zone_name   varchar(30) references zones (name),
    max_height  int not null
);

-- entity
create table rooms
(
    room_id     serial primary key,
    building_id int references buildings (building_id),
    room_number int not null,
    floor       int not null,
    room_type   int not null check ( room_type in (1, 2, 3, 4) ),
    gender      int not null check ( gender in (1, 2)),
    -- single, double, triple, quad
    description text
);

-- entity
create table users
(
    campus_id varchar(8) primary key,
    password  text not null,
    role      int  not null check ( role in (1, 2, 3) )
    -- 1: teacher, 2: student, 3: admin
);

create extension pgcrypto;
-- use crypt() and gen_salt() to encrypt password
insert into users (campus_id, password, role)
values ('12110517', crypt('user_password', gen_salt('bf')), 2);


-- entity
create table students
(
    student_id varchar(8) primary key references users (campus_id),
    name       varchar(20) not null,
    gender     int         not null check ( gender in (1, 2) ),    -- 1: male 2:female
    degree     int         not null check ( degree in (1, 2, 3) ), -- 1: master 2: doctor 3: postdoc
    major      varchar(20) not null,
    info       text                                                --personal information
);

-- entity
create table teachers
(
    teacher_id varchar(8) primary key references users (campus_id),
    name       varchar(20) not null
);

-- entity
create table teams
(
    team_id    serial primary key,
    creator_id varchar(8) not null references students (student_id)
);

-- relationship
create table team_members
(
    team_id    int references teams (team_id),
    student_id varchar(8) references students (student_id),
    primary key (team_id, student_id)
);

-- entity
create table comments
(
    comment_id serial primary key,
    author_id  varchar(8) not null references users (campus_id),
    room_id    int        not null references rooms (room_id),
    content    text       not null,
    time       timestamp default now()
);

-- entity
create table second_comments
(
    parent_comment_id int references comments (comment_id),
    author_id         varchar(8) not null references users (campus_id),
    content           text       not null,
    time              timestamp default now(),
    primary key (parent_comment_id, author_id, time)
);

-- relationship (one to one)
create table selections
(
    team_id int references teams (team_id),
    room_id int references rooms (room_id),
    primary key (team_id, room_id)
);

-- relationship (many to many)
create table favorites
(
    team_id int references teams (team_id),
    room_id int references rooms (room_id),
    primary key (team_id, room_id)
);

-- entity
create table notifications
(
    notification_id serial primary key,
    content         text not null,
    time            timestamp default now()
);

-- relationship (many to many)
create table notifications_to_users
(
    notification_id int references notifications (notification_id),
    user_id         varchar(8) references users (campus_id),
    primary key (notification_id, user_id)
);


-- relationship (many to many)
create table invitations
(
    student_id    varchar(8) not null references students (student_id),
    team_id       int        not null references teams (team_id),
    is_invitation boolean    not null,
    primary key (student_id, team_id, is_invitation)
);








