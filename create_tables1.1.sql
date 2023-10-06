-- Create Zone table
create table zone
(
    id   serial primary key,
    name varchar(30) not null
);

-- Create Building table
create table building
(
    id      int primary key,
    zone_id int not null,
    height  int not null,
    foreign key (zone_id) references zone (id)
);

-- Create Floor table
create table floor
(
    id          serial primary key,
    building_id int,
    number      int not null,
    foreign key (building_id) references building (id)
);

-- Create Room table
create table room
(
    id          serial primary key,
    floor_id    int,
    room_number int not null,
    room_type   int not null check (room_type in (1, 2, 3, 4)),
    description text,
    foreign key (floor_id) references floor (id)
);

-- Create User table
create table users
(
    id       serial primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    role     varchar(255) not null check (role in ('student', 'faculty', 'admin'))
);

-- Create Student table
create table student
(
    student_id serial primary key,
    user_id    int not null,
    name       varchar,
    sex        varchar,
    degree     varchar,
    major      varchar,
    foreign key (user_id) references users (id)
);

-- Create Faculty table
create table faculty
(
    faculty_id serial primary key,ø
    user_id    int not null,
    name       varchar,
    foreign key (user_id) references users (id)
);

-- Create SelectionPeriod table
create table selection_period
(
    id         serial primary key,
    start_time timestamp not null,
    end_time   timestamp not null
);

-- Create Team table
create table team
(
    id        serial primary key,
    team_name varchar(255) not null,
    leader_id int          not null,
    foreign key (leader_id) references student (student_id)
);

-- Create TeamMember table
create table team_member
(
    id         serial primary key,
    team_id    int not null,
    student_id int not null,
    foreign key (team_id) references team (id),
    foreign key (student_id) references student (student_id)
);

-- Create FavoriteRoom table
create table favorite_room
(
    id         serial primary key,
    student_id int not null,
    room_id    int not null,
    foreign key (student_id) references student (student_id),
    foreign key (room_id) references room (id)
);

-- Create Comment table
create table comment
(
    id                serial primary key,
    student_id        int  not null,
    room_id           int  not null,
    comment_text      text not null,
    parent_comment_id int,
    foreign key (student_id) references student (student_id),
    foreign key (room_id) references room (id),
    foreign key (parent_comment_id) references comment (id)
);

-- Create Selection table
create table selection
(
    id                  serial primary key,
    team_id             int not null,
    room_id             int not null,
    selection_period_id int not null,
    foreign key (team_id) references team (id),
    foreign key (room_id) references room (id),
    foreign key (selection_period_id) references selection_period (id)
);

-- Create Personal table
create table personal
(
    id                  serial primary key,
    student_id          int        not null,
    mbti                varchar(7) not null,
    sleep_time          time       not null,
    wake_time           time       not null,
    description         text,
    exp_zone            int,
    exp_building_number int,
    exp_room_type       int,
    foreign key (student_id) references student (student_id)
);
