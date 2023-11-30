-- favorite_rooms表, 在删除team的时候，删除favorite_rooms表中的记录
-- 或在删除room的时候，删除favorite_rooms表中的记录

-- 移除原来的外键约束
-- alter table favorite_rooms
--     drop constraint;
-- alter table favorite_rooms
--     add constraint fk_favorite_rooms_teams
--         foreign key (team_id) references teams (team_id)
--             on delete cascade;
-- alter table favorite_rooms
--     add constraint fk_favorite_rooms_rooms
--         foreign key (room_id) references rooms (room_id)
--             on delete cascade;


-- 插入users表的时候根据role，自动在students或者teachers表中插入一条记录
create or replace function insert_student_or_teacher()
    returns TRIGGER as
$$
begin
    if new.role = 'student' then
        insert into students (student_id)
        values (new.campus_id);
    elsif new.role = 'teacher' then
        insert into teachers (teacher_id)
        values (new.campus_id);
    end if;
    return new;
end;
$$ language plpgsql;

-- trigger
create trigger insert_student_or_teacher
    after insert
    on users
    for each row
execute procedure insert_student_or_teacher();


-- 删除teams表的时候，将students表中的team_id置为null
create or replace function team_delete_students()
    returns TRIGGER as
$$
begin
    update students
    set team_id = null
    where team_id = old.team_id;
    return old;
end;
$$ language plpgsql;

-- trigger
create trigger team_delete_students
    before delete
    on teams
    for each row
execute procedure team_delete_students();


-- 删除teams表的时候，删除favorite_rooms表中的记录
create or replace function team_delete_favorite_rooms()
    returns TRIGGER as
$$
begin
    delete
    from favorite_rooms
    where team_id = old.team_id;
    return old;
end;
$$ language plpgsql;

-- trigger
create trigger team_delete_favorite_rooms
    before delete
    on teams
    for each row
execute procedure team_delete_favorite_rooms();


-- 删除rooms表的时候，删除favorite_rooms表中的记录
create or replace function room_delete_favorite_rooms()
    returns TRIGGER as
$$
begin
    delete
    from favorite_rooms
    where room_id = old.room_id;
    return old;
end;
$$ language plpgsql;

-- trigger
create trigger room_delete_favorite_rooms
    before delete
    on rooms
    for each row
execute procedure room_delete_favorite_rooms();

create or replace function delete_student()
    returns TRIGGER as
$$
begin
    -- 如果该学生是一个team的creator，则删除该team，并将原来team中的学生的teamId设置为null
    if exists (select 1 from teams where creator_id = old.student_id) then
        delete from teams where creator_id = old.student_id;
        update students set team_id = null where team_id = old.team_id;
    else
        -- 如果该学生不是任何team的creator，则直接删除该学生
        delete from students where student_id = old.student_id;
    end if;

    return old;
end;
$$ language plpgsql;

-- 创建触发器，在删除student时触发delete_student函数
create trigger trg_delete_student
    before delete
    on students
    for each row
execute function delete_student();



-- 删除student的时候，连带删除user表中的记录，处理好关联user的外键
create or replace function after_delete_student()
    returns TRIGGER as
$$
begin
    delete from teams where teams.team_id = old.team_id;
    delete
    from users
    where campus_id = old.student_id
      and role = 'student';
    return old;
end ;
$$ language plpgsql;

-- trigger
create trigger after_delete_student
    after delete
    on students
    for each row
execute procedure after_delete_student();





