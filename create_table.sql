--Strong entities
create table buildings
(
    building_number int primary key,
    district varchar not null ,
    height int not null
);

create table rooms
(
    room_id serial primary key ,
    room_number varchar not null ,
    floor int not null ,
    type int not null ,
    favorited int,
    description text
);

create table students
(
    student_id varchar primary key ,
    name varchar not null ,
    sex varchar not null ,
    degree varchar not null,
    speciality varchar not null
);

create table teams
(
    team_number serial primary key,
    name varchar
);

create table replies
(
    reply_id serial primary key ,
    content text,
    star int,
    author_id varchar,
    foreign key (author_id) references students(student_id)
);
--Weak entities
create table second_replies
(
    reply_id int,
    author_id varchar,
    star int,
    primary key (reply_id, author_id, star),
    foreign key (reply_id) references replies(reply_id),
    foreign key (author_id) references students(student_id)
);

create table expect
(
    student_id varchar,
    building_number int,
    floor int,
    type int,
    primary key (student_id, building_number, floor, type),
    foreign key (student_id) references students(student_id),
    foreign key (building_number) references buildings(building_number)
);

create table personal
(
    student_id varchar primary key ,
    sleep_time time,
    hobby varchar,
    mbti varchar,
    description text,
    foreign key (student_id) references students(student_id)
);

create table room_in_building
(
    building_number int,
    room_id int primary key ,
    foreign key(building_number) references buildings(building_number),
    foreign key (room_id) references rooms(room_id)
);

create table team_member
(
    student_id varchar primary key ,
    team_number int,
    foreign key (student_id) references students(student_id),
    foreign key (team_number) references teams(team_number)
);

create table favorite
(
    room_id int,
    team_number int,
    primary key (room_id, team_number),
    foreign key (room_id) references rooms(room_id),
    foreign key (team_number) references teams(team_number)
);

create table selection
(
    room_id int primary key ,
    team_number int unique ,
    foreign key (room_id) references rooms(room_id),
    foreign key (team_number) references teams(team_number)

);

create table reply_list
(
    reply_id int primary key ,
    room_id int,
    foreign key (reply_id) references replies(reply_id),
    foreign key (room_id) references rooms(room_id)
);