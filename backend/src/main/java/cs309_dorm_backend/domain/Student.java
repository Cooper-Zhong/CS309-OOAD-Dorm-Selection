package cs309_dorm_backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
public class Student {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private int student_id;
    @NotNull // null then cannot be executed
    private String name;
    private String sex;
    private String degree;
    private String major;
    private int user_id;

    public int getStudent_id() {
        return student_id;
    }
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String student_name) {
        this.name = student_name;
    }
    public String getSex(){
        return sex;
    }
    public void setSex(String sex){
        this.sex = sex;
    }
    public String getDegree(){
        return degree;
    }
    public void setDegree(String degree){
        this.degree = degree;
    }
    public String getMajor(){
        return major;
    }
    public void setMajor(String major){
        this.major = major;
    }
    public int getUser_id(){
        return user_id;
    }
    public void setUser_id(int user_id){
        this.user_id = user_id;
    }


}

