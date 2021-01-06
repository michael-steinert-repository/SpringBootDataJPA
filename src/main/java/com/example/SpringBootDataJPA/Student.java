package com.example.SpringBootDataJPA;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Student")
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(name = "email_unique", columnNames = "email")
        }
)
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "student_id",
            updatable = false
    )
    private Long studentId;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "age",
            nullable = false
    )
    private Integer age;

    @OneToOne(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private StudentIdCard studentIdCard;

    @OneToMany(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Book> bookList = new ArrayList<>();

    /*
    //Automatically generated Table Enrolment
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "enrolment",
            joinColumns = @JoinColumn(
                    name = "student_id",
                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "course_id",
                    foreignKey = @ForeignKey(name = "enrolment_course_id_fk")
            )
    )
    private List<Course> courseList = new ArrayList<>();
    */

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "student"
    )
    private List<Enrolment> enrolmentList = new ArrayList<>();

    public Student(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Student() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setStudentIdCard(StudentIdCard studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void addBook(Book book) {
        if(!this.bookList.contains(book)) {
            this.bookList.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        if(this.bookList.contains(book)) {
            this.bookList.remove(book);
            book.setStudent(null);
        }
    }

    /*
    //Automatically generated Table Enrolment
    public List<Course> getCourseList() {
        return courseList;
    }

    public void addCourse(Course course) {
        courseList.add(course);
        course.getStudentList().add(this);
    }

    public void removeCourse(Course course) {
        courseList.remove(course);
        course.getStudentList().remove(this);
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
        return "Student{" +
                "StudentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
