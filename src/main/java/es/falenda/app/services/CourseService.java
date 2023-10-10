package es.falenda.app.services;

import es.falenda.app.models.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAll();
    Course findById(String id);
   Course findCourseByTitle(String title);
    void create(Course course);
    void update(String oldTitle, String newTitle, String description);
    void delete(String title);

}
