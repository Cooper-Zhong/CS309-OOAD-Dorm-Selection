## ERD说明
### Strong Entities
* #### buildings
	* <u>building_number</u>, (int), 宿舍楼的楼号
	* district, (varchar), 宿舍楼的区域
	* height, (int), 宿舍楼的总层数
* #### rooms
	* <u>room_id</u>, (serial) ,由于不同宿舍楼的房间号可能重复，需要有一个自增的编号来区别不同房间
	* room_number, (varchar), 房间号，不同宿舍楼可能相同，可能带英文字母
	* floor, (int), 该房间所在的楼层
	* type, (int), [1, 4]范围的一个数，表示几人间
	* favorited, (int), 表示该房间被几个队伍收藏了
	* description, (text), 房间的描述
* #### students
	* <u>student_id</u>, (varchar), 学生的学号
	* name, (varchar), 学生的姓名
	* sex, (varchar), male or female，学生的性别
	* degree, (varchar), master or doctor,学生的在读学位
	* speciality, (varchar), 学生的就读专业
	* 注意，这里的所有值都是not null的，是存在的student必须要有的属性
* #### teams
	* <u>team_number</u>, (serial), 队伍的编号
	* name, (varchar), 队伍的名称，默认为anonymous
* #### replies
	* <u>reply_id</u>, (serial), 一级回复的编号
	* content, (text), 回复的内容
	* star, (int), 回复的点赞数
	* author_id, (varchar), 评论者的学号

### Weak Entities
* #### second_replies
	* author_id, (varchar), 二级评论者的学号
	* content, (text), 二级回复的内容
	* star, (int), 二级回复的点赞数
* #### expect
	* 学生期待选择的房间信息，可以用于组队依据，每个学生可以有多条
	* building_number, (int), 期待的楼栋
	* floor, (int), 期待的楼层
	* type, (int), 期待的房型
	* 三个内容和student_id组成组件，所有内容不为空，如果某个属性任意都可用0来表示

* #### personal
	* 学生个人的信息，注意这里跟students不同的是这些属性在学生存在时不一定需要完善，是组队筛选的辅助信息。
	* sleeping_time (time), 休息时间
	* hobby (varchar), 爱好
	* mbti (varchar), 人格类型
	* description (text), 个人介绍
	* 每个学生只能有一条，以student_id为主键。

### Relations
* #### room_in_building
	* $buildings \leftarrow rooms$
	* $one-to-many$
	* 表示某个房间在哪栋宿舍楼中
* #### team_member
	* $students \rightarrow teams$
	* $many-to-one$
	* 表示某个学生在哪个队伍
	* 其中同一个team应在该表中出现1..4次，表示一个队伍如果存在至少要有1个人，最多有4个人。
	* 一个student最多出现1次，也可以不出现，表示可以暂未加入队伍，最多加入一个队伍
* #### favorite
	*  $rooms -- teams$
	*  $many-to-many$
	*  表示队伍收藏的房间，可以收藏多个，也可以不收藏
* #### selection
	* $rooms -- teams$
	* $one-to-one$
	* 表示最终以队伍为单位选取的房间，房间和队伍一一匹配
* #### reply_list
	* $rooms \Leftarrow replies$ 
	* $one-to-many$
	* replies为fully participation，所有reply必须依附于一个room
* 弱实体的关系由于不建表因此不描述。
