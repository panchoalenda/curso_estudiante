package es.falenda.app.repositories;

import es.falenda.app.models.Course;
import es.falenda.app.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    @Query("""
        SELECT c
        FROM Course c
        WHERE c.title = :title
      """)
    Course findCourseByTitle(String title);

    @Query("""
        SELECT COUNT(s)
        FROM Student s
        WHERE s.course.id = :id
      """)
    Integer countStudentByCourse(String id);

    @Query("""
      SELECT s
      FROM Student s
      WHERE s.approved = :condition
      AND s.course.id = :idCourse
      """)
    List<Student> findByText(Boolean condition, String idCourse);
}
