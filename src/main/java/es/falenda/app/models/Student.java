package es.falenda.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "students")
public class Student implements Serializable, Comparable<Student> {
    @Id
    @UuidGenerator
    private String id;

    @NotEmpty(message = "El nombre es obligatorio")
    private String        name;
    @NotEmpty(message = "El apellido es obligatorio")
    private String        lastName;
    @Column(name = "date_birth")
    private LocalDate     dateBirth;
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    @Column(name = "approved")
    private Boolean       approved;
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    @OneToOne
    private Image  image;

    public Student() {

    }

    public Student(String name, String lastName, LocalDate dateBirth, Course course, Image image) {
        this.name = name;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.registrationDate = LocalDate.now();
        this.approved = false;
        this.course = course;
        this.image = image;
    }
    public Student(String name, String lastName, LocalDate dateBirth, Boolean approved, Course courseBd) {
        this.name = name;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.registrationDate = LocalDate.now();
        this.approved = approved;
        this.course = courseBd;
    }

    public Student(String name, String lastName, LocalDate dateBirth, Boolean approved, Course course, Image image) {
        this.name = name;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.registrationDate = LocalDate.now();
        this.approved = approved;
        this.course = course;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Student{" +
          "id='" + id + '\'' +
          ", name='" + name + '\'' +
          ", lastName='" + lastName + '\'' +
          ", dateBirth=" + dateBirth +
          ", registrationDate=" + registrationDate +
          ", approved=" + approved +
          ", course=" + course +
          '}';
    }

    @Override
    public int compareTo(Student student) {
        return this.lastName.compareTo(student.getLastName());
    }

}
