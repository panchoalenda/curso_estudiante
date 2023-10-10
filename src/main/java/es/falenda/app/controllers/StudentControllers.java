package es.falenda.app.controllers;

import es.falenda.app.models.Course;
import es.falenda.app.models.Student;
import es.falenda.app.repositories.CourseRepository;
import es.falenda.app.repositories.StudentRepository;
import es.falenda.app.services.CourseService;
import es.falenda.app.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/estudiante")
public class StudentControllers {

    @Autowired
    StudentService    studentService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository  courseRepository;
    @Autowired
    CourseService    courseService;


    @GetMapping
    public String index(ModelMap model) {
        model.put("students", studentService.findAll());
        model.put("courses", courseRepository.findAll());
        return "crear-alumno.html";
    }

    @GetMapping("/lista-alumnos")
    public String list(ModelMap model) {
        List<Student> students = studentService.findAll();
        Collections.sort(students);
        model.put("students", students);
        model.put("courses", courseRepository.findAll());
        return "lista-alumnos.html";
    }

    @PostMapping("/buscar")
    public String search(@RequestParam("text") String text, ModelMap model){
        List<Student> students = studentRepository.findByText(text);

        model.put("students", students);
        return "lista-alumnos-busqueda.html";

    }
    @GetMapping("/detalle/{id}")
    public String show(@PathVariable String id, ModelMap model) {
        Student student = studentService.findById(id);
        System.out.println("Datos del Alumno" + student);
        model.addAttribute("student", student);
        return "mostrar-alumno.html";
    }

    @PostMapping
    public String create(@RequestParam(name = "name") String name, @RequestParam(name = "lastName") String lastName,
                         @RequestParam("dateBirth") LocalDate dateBirth,
                         @RequestParam("course") String course, ModelMap model, @RequestParam("archive") MultipartFile archive) {

        try {
            studentService.create(name, lastName, dateBirth, course, archive);
            model.put("exito", "El Alumno se guardo correctamente");
            model.put("students", studentService.findAll());
            model.put("courses", courseRepository.findAll());
            return "lista-alumnos.html";

        } catch (IllegalArgumentException e) {
            model.put("students", studentService.findAll());
            model.put("courses", courseRepository.findAll());
            model.put("error", e.getMessage());
            return "lista-alumnos.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap model) {
        Student student = studentService.findById(id);
        List<Course> courses = courseService.findAll();
        model.put("student", student);
        model.put("courses", courses);
        return "modificar-alumno.html";
    }

    @PostMapping("/modificar")
    public String update(@RequestParam(name = "id") String id, @RequestParam(name = "name") String name, @RequestParam(name = "lastName") String lastName,
                         @RequestParam("dateBirth") LocalDate dateBirth, @RequestParam("approved") Boolean approved,
                         @RequestParam("course") String course, ModelMap model, @RequestParam("archive") MultipartFile archive) {

        model.put("students", studentService.findAll());
        model.put("courses", courseRepository.findAll());

        try {
            studentService.update(id, name, lastName, dateBirth, approved, course, archive);
            model.put("exito", "El Alumno se modificó correctamente");
            return "lista-alumnos.html";

        } catch (IllegalArgumentException e) {
            model.put("error", e.getMessage());
                return "lista-alumnos";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable String id, ModelMap model) {

        try {
            studentService.delete(id);
            model.put("exito", "El Alumno se eliminó correctamente");
        } catch (IllegalArgumentException e) {
            model.put("error", e.getMessage());
        }
            model.put("students", studentService.findAll());
            model.put("courses", courseRepository.findAll());
            return "lista-alumnos.html";
    }



}
