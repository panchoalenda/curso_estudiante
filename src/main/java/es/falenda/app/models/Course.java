package es.falenda.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "courses")
public class Course implements Serializable, Comparable<Course> {
    @Id
    @UuidGenerator
    private String    id;
    @NotEmpty(message = "El título no puede estar vacío.")
    @Column(name = "title")
    private String    title;
    @NotEmpty(message = "La descripción no puede estar vacía.")
    @Column(name = "description")
    private String    description;
    @Column(name = "create_date")
    private LocalDate createDate;

    public Course() {
    }

    public Course(String title, String description) {
        this.title = title;
        this.description = description;
        this.createDate = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int compareTo(Course course) {
        return this.title.compareTo(course.getTitle());
    }
}