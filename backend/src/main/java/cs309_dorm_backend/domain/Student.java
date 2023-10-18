package cs309_dorm_backend.domain;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @JoinColumn(name = "student_id", referencedColumnName = "campus_id")
    private String studentId;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private int sex;

    @Column(name = "degree")
    private int degree;

    @Column(name = "major")
    private String major;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team; //当学生不属于任何团队时，team 属性将为 null

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

