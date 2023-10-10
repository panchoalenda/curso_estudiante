package es.falenda.app.services;

import es.falenda.app.models.Course;
import es.falenda.app.models.Image;
import es.falenda.app.models.Student;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findById(String id);

    Student findStudentByTitle(String lastName);

    void create(String name, String lastName, LocalDate dateBirth, String course, MultipartFile archive);

    void update(String id, String name, String lastName, LocalDate dateBirth, Boolean approved, String course, MultipartFile archive);

    void delete(String id);


}
