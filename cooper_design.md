## Entities

### Zone(区划)

- id (Primary Key)
- name (Zone Name)

```sql
CREATE TABLE Zone (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL
);
```

### Building(楼栋)

- id (Primary Key) 楼号
- zone_id (Foreign Key referencing `Zone`)
- height (层数)

```sql
CREATE TABLE Building (
    id INT PRIMARY KEY,
    zone_id INT NOT NULL,
    height INT NOT NULL,
    FOREIGN KEY (zone_id) REFERENCES Zone(id)
);
```

### Floor (楼层)

- id (Primary Key)
- building_id (Foreign Key referencing `Building`)
- number (Floor Number)

```sql
CREATE TABLE Floor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    building_id INT,
    number INT NOT NULL,
    FOREIGN KEY (building_id) REFERENCES Building(id)
);
```

### Room (房间)

- id (Primary Key)
- floor_id (Foreign Key referencing `Floor`)
- room_number (Room Number) 房间号
- room_type (Room Type) [1,4]
- description (Room Description)

```sql
CREATE TABLE Room (
    id INT PRIMARY KEY AUTO_INCREMENT,
    floor_id INT,
    room_number INT NOT NULL, (或者varchar可能带字母)
    room_type INT NOT NULL CHECK (room_type IN (1, 2, 3, 4)),
    description TEXT,
    FOREIGN KEY (floor_id) REFERENCES Floor(id)
);
```

### User

- id (Primary Key)
- username (Username)
- password (Password)
- role (student or faculty or admin)

```sql
CREATE TABLE User (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL CHECK (role IN ('student', 'faculty', 'admin'))
);
```

### Student

- student_id (Primary Key)
- user_id (Foreign Key referencing `User`)
- name
- sex (性别)
- degree (学位)
- major (专业)

```sql
CREATE Table Student(
    student_id INT PRIMARY KEY,
    user_id INT NOT NULL,
    name varchar,
    sex varchar,
    degree varchar,
    major varchar,
    FOREIGN KEY (user_id) REFERENCES User(id)
);
```

### Faculty

- faculty_id (Primary Key)
- user_id (Foreign Key referencing `User`)
- name varchar

```sql
CREATE TABLE faculty (
    faculty_id INT PRIMARY KEY,
    user_id INT NOT NULL,
    name varchar,
    FOREIGN KEY (user_id) REFERENCES User(id)
);
```

### SelectionPeriod (设计错峰选择时间段)

- id (Primary Key)
- start_date (Start Date)
- end_date (End Date)

```sql
CREATE TABLE SelectionPeriod (
    id INT PRIMARY KEY AUTO_INCREMENT,
    start_time timestamp NOT NULL,
    end_time timestamp NOT NULL
);
```

### Team (队伍)

- id (Primary Key)
- team_name (Team Name)
- leader_id (Foreign Key referencing `Student`)

表示某个学生在哪个队伍
其中同一个team应在该表中出现1..4次，表示一个队伍如果存在至少要有1个人，最多有4个人。一个student最多出现1次，也可以不出现，表示可以暂未加入队伍，最多加入一个队伍

```sql
CREATE TABLE Team (
    id INT PRIMARY KEY AUTO_INCREMENT,
    team_name VARCHAR(255) NOT NULL,
    leader_id INT NOT NULL,
    FOREIGN KEY (leader_id) REFERENCES Student(student_id)
);
```

### TeamMember (队伍成员)

- id (Primary Key)
- team_id (Foreign Key referencing `Team`)
- student_id (Foreign Key referencing `Student`)

```sql
CREATE TABLE TeamMember (
    id INT PRIMARY KEY AUTO_INCREMENT,
    team_id INT NOT NULL,
    student_id INT NOT NULL,
    FOREIGN KEY (team_id) REFERENCES Team(id),
    FOREIGN KEY (student_id) REFERENCES Student(student_id)
);
```

> To create a team with variable sizes, you would:

- Insert a row into the `Team` table to create the team and specify the team leader.
- Insert rows into the `TeamMember` table to associate the desired students with that team.

### FavoriteRoom (收藏)

注意一定数量上限

- id (Primary Key)
- student_id (Foreign Key referencing `Student`)
- room_id (Foreign Key referencing `Room`)

```sql
CREATE TABLE FavoriteRoom (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    room_id INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (room_id) REFERENCES Room(id)
);
```

### Comment (评论+回复)

- id (Primary Key)
- student_id (Foreign Key referencing `Student`)
- room_id (Foreign Key referencing `Room`)
- comment_text (Comment Text)
- parent_comment_id (Foreign Key referencing `Comment` or **NULL**) (为Null则是comment，否则是reply)

```sql
CREATE TABLE Comment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    room_id INT NOT NULL, 必须依附于一个房间
    comment_text TEXT NOT NULL,
    parent_comment_id INT,
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (room_id) REFERENCES Room(id),
    FOREIGN KEY (parent_comment_id) REFERENCES Comment(id)
);
```

### Selection (选房)

- id (Primary Key)
- team_id (Foreign Key referencing `Team`)
- room_id (Foreign Key referencing `Room`)
- selection_period_id (Foreign Key referencing `SelectionPeriod`)

```sql
CREATE TABLE Selection (
    id INT PRIMARY KEY AUTO_INCREMENT,
    team_id INT NOT NULL,
    room_id INT NOT NULL,
    selection_period_id INT NOT NULL,
    FOREIGN KEY (team_id) REFERENCES Team(id),
    FOREIGN KEY (room_id) REFERENCES Room(id),
    FOREIGN KEY (selection_period_id) REFERENCES SelectionPeriod(id)
);
```

### Personal (个人信息)

组队的辅助信息

- id (Primary Key)
- student_id (Foreign Key referencing `Student`)
- mbti (16 MBTI)
- sleep_time (睡觉时间)
- wake_time (起床时间)
- description (个人描述)
- exp_zone (期望区划)
- exp_building_number (期望楼栋号)
- exp_room_type (期望房间类型)

```sql
CREATE TABLE Personal (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    mbti VARCHAR(7) NOT NULL,
    sleep_time TIME NOT NULL,
    wake_time TIME NOT NULL,
    description TEXT,
    exp_zone INT,
    exp_building_number INT,
    exp_room_type INT,
    FOREIGN KEY (student_id) REFERENCES Student(student_id)
);
```
