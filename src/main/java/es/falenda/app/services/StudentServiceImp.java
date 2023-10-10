package es.falenda.app.services;

import es.falenda.app.models.Course;
import es.falenda.app.models.Image;
import es.falenda.app.models.Student;
import es.falenda.app.repositories.CourseRepository;
import es.falenda.app.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImp implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ImageService     imageService;

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return this.studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(String id) {
        return this.studentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Student findStudentByTitle(String lastName) {
        return this.studentRepository.findStudentByLastName(lastName);
    }

    @Override
    @Transactional
    public void create(String name, String lastName, LocalDate dateBirth, String course, MultipartFile archive) {
        Image image = this.imageService.save(archive);
        Course courseBd = this.courseRepository.findCourseByTitle(course);
        Student student = new Student(name, lastName, dateBirth, courseBd, image);
        this.studentRepository.save(student);
    }

    @Override
    @Transactional
    public void update(String id, String name, String lastName, LocalDate dateBirth,
                       Boolean approved, String course, MultipartFile archive) {
        Student student = null;

        Course courseBd = this.courseRepository.findCourseByTitle(course);
        Optional<Student> studentOptional = this.studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            student = studentOptional.get();
            student.setName(name);
            student.setLastName(lastName);
            student.setDateBirth(dateBirth);
            student.setApproved(approved);
            student.setCourse(courseBd);
            if(!archive.isEmpty()) {
                Image image = this.imageService.save(archive);
                student.setImage(image);
            }
            this.studentRepository.save(student);
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        this.studentRepository.deleteById(id);
    }
}
