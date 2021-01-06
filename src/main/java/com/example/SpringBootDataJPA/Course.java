package com.example.SpringBootDataJPA;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Course")
@Table(name = "course")
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    @Column(
            name = "course_id",
            updatable = false
    )
    private Long courseId;

    @Column(
            name = "course_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String courseName;

    @Column(
            name = "department",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String department;

    /*
    //Automatically generated Table Enrolment
    @ManyToMany(mappedBy = "courseList")
    private List<Student> studentList = new ArrayList<>();
     */

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "course"
    )
    private List<Enrolment> enrolmentList = new ArrayList<>();

    public Course(String courseName, String department) {
        this.courseName = courseName;
        this.department = department;
    }

    public Course() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /*
    //Automatically generated Table Enrolment
    public List<Student> getStudentList() {
        return studentList;
    }
    */

    public List<Enrolment> getEnrolmentList() {
        return enrolmentList;
    }

    public void addEnrolment(Enrolment enrolment) {
        if(!enrolmentList.contains(enrolment)) {
            enrolmentList.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment) {
        if(enrolmentList.contains(enrolment)) {
            enrolmentList.remove(enrolment);
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
