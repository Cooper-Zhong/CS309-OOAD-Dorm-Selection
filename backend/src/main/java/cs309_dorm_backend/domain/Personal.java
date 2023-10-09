package cs309_dorm_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

//- id (Primary Key)
//        - student_id (Foreign Key referencing `Student`)
//        - mbti (16 MBTI)
//        - sleep_time (睡觉时间)
//        - wake_time (起床时间)
//        - description (个人描述)
//        - exp_zone (期望区划)
//        - exp_building_number (期望楼栋号)
//        - exp_room_type (期望房间类型)
@Entity
public class Personal {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private long id;
    @NotNull // null then cannot be executed
    private int student_id;
    private String mbti;
    private String sleep_time;
    private String wake_time;
    private String description;
    private String exp_zone;
    private String exp_building_number;
    private String exp_room_type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public int getStudent_id() {
        return student_id;
    }
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
    public String getMbti() {
        return mbti;
    }
    public void setMbti(String mbti) {
        this.mbti = mbti;
    }
    public String getSleep_time() {
        return sleep_time;
    }
    public void setSleep_time(String sleep_time) {
        this.sleep_time = sleep_time;
    }
    public String getWake_time() {
        return wake_time;
    }
    public void setWake_time(String wake_time) {
        this.wake_time = wake_time;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getExp_zone() {
        return exp_zone;
    }
    public void setExp_zone(String exp_zone) {
        this.exp_zone = exp_zone;
    }
    public String getExp_building_number() {
        return exp_building_number;
    }
    public void setExp_building_number(String exp_building_number) {
        this.exp_building_number = exp_building_number;
    }
    public String getExp_room_type() {
        return exp_room_type;
    }
    public void setExp_room_type(String exp_room_type) {
        this.exp_room_type = exp_room_type;
    }
}

