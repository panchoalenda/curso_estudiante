package es.falenda.app.controllers;

import es.falenda.app.models.Course;
import es.falenda.app.models.Student;
import es.falenda.app.repositories.CourseRepository;
import es.falenda.app.repositories.StudentRepository;
import es.falenda.app.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/curso")
public class CourseControllers {
    @Autowired
    private HomeController homeController;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @GetMapping
    public String index(ModelMap model) {

        return "crear-curso.html";
    }

    @PostMapping
    public String create(@Valid Course course,
                         BindingResult result,
                         ModelMap model) {
        if(result.hasErrors()){
            Map<String,String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error-> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            model.addAttribute("errors", errors);
            return "crear-curso";
        }

        List<Course> courses = this.courseService.findAll();

        model.addAttribute("courses", courses);
        model.addAttribute("course", course);

        this.courseService.create(course);

        courses = this.courseService.findAll();

        Collections.sort(courses);
        model.addAttribute("courses", courses);
        return "lista-cursos.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap model) {
        Course course = this.courseService.findById(id);
        List<Course> courses = this.courseService.findAll();
        Collections.sort(courses);
        model.put("courses", courses);
        model.put("course", course);
        return "modificar-curso.html";
    }

    @PostMapping("/modificar")
    public String update(@RequestParam(name = "id") String id, @RequestParam(name = "title") String title, @RequestParam(name = "description") String description, ModelMap model) {

        try {
            this.courseService.update(id, title, description);
            model.put("exito", "El Curso se modificó correctamente");
            List<Course> courses = this.courseService.findAll();
            Collections.sort(courses);
            model.addAttribute("courses", courses);
            return "lista-cursos.html";

        } catch (IllegalArgumentException e) {
            model.put("error", e.getMessage());
            List<Course> courses = this.courseService.findAll();
            Collections.sort(courses);
            model.addAttribute("courses", courses);
            return "lista-cursos.html";
        }
    }

    @GetMapping("/{id}")
    public String show(@PathVariable String id, ModelMap model) {
        model.put("course", this.courseService.findById(id));
        model.put("count", this.courseRepository.countStudentByCourse(id));
        model.put("students", this.studentRepository.findStudentsbyCourse(id));
        return "mostrar-curso.html";
    }

    @GetMapping("/lista-cursos")
    public String list(ModelMap model) {

        List<Course> courses = this.courseService.findAll();
        Collections.sort(courses);
        model.addAttribute("courses", courses);

        return "lista-cursos.html";
    }

    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable(name = "id") String id, ModelMap model) {
        this.courseService.delete(id);
        List<Course> courses = this.courseService.findAll();
        Collections.sort(courses);
        model.addAttribute("courses", courses);

        return "lista-cursos.html";
    }

    @PostMapping("/buscar/{id}")
    public String search(@RequestParam("text") String text, @PathVariable("id") String id, ModelMap model) {

        boolean condition = false;
        text = text.toLowerCase();

        if (text.equals("aprobado")) {
            condition = true;
        }
        if (text.equals("cursando")) {
            condition = false;
        }
        List<Student> students = this.courseRepository.findByText(condition, id);

        model.put("text", text);
        model.put("students", students);
        model.put("course", this.courseService.findById(id));
        return "mostrar-curso-busqueda.html";

    }

}

