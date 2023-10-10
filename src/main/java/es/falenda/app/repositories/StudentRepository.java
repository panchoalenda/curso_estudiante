package es.falenda.app.repositories;

import es.falenda.app.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("""
      SELECT s
      FROM Student s
      WHERE s.lastName=:lastName
      """)
    Student findStudentByLastName(String lastName);

    @Query("""
      SELECT s
      FROM Student s
      WHERE s.course.id=:id
      """)
    List<Student> findStudenyByCourse(String id);

    @Query("""
      SELECT s
      FROM Student s
      WHERE s.name LIKE CONCAT('%', :text, '%')
      OR s.lastName LIKE CONCAT('%', :text, '%')
      OR s.course.title LIKE CONCAT('%', :text, '%')
      """)
    List<Student> findByText(String text);

    @Query("""
      SELECT s
      FROM Student s
      WHERE s.course.id = :id
      """)
    List<Student> findStudentsbyCourse(String id);
}
