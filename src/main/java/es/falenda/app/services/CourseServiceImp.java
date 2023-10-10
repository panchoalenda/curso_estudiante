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
public class CourseServiceImp implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    public Course findById(String id){
        return this.courseRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Course findCourseByTitle(String title) {
        Course course = this.courseRepository.findCourseByTitle(title);
        if (course == null) {
            throw new RuntimeException("No existe un Curso con ese Título.");
        }
        return course;
    }

    @Override
    @Transactional
    public void create(Course course) {

       /* if (course.getTitle() == null || course.getTitle().length() < 5) {
            throw new RuntimeException("El Título no puede estar vacío y debe contener al menos 5 caracteres");
        }
        if (course.getDescription() == null || course.getDescription().length() < 5) {
            throw new RuntimeException("La Descripción no puede estar vacío y debe contener al menos 5 caracteres");
        }*/

        this.courseRepository.save(course);
    }

    @Override
    @Transactional
    public void update(String id, String title, String descripción) {
        Course course = null;

        Optional<Course> courseOptional = this.courseRepository.findById(id);

        if (courseOptional.isPresent()) {
            course = courseOptional.get();
            course.setTitle(title);
            course.setDescription(descripción);

            this.courseRepository.save(course);
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
       Optional<Course> courseBd = this.courseRepository.findById(id);

        List<Student> students = this.studentRepository.findStudenyByCourse(id);

        for(Student s : students){
            this.studentRepository.deleteById(s.getId());
        }
       /* if (courseBd.isPresent()) {
            courseRepository.delete(courseBd.get());
        }*/
        courseBd.ifPresent(course -> this.courseRepository.delete(course));
    }
}
