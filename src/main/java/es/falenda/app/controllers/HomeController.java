package es.falenda.app.controllers;

import es.falenda.app.repositories.CourseRepository;
import es.falenda.app.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    CourseService courseService;
    @GetMapping
    public String index(ModelMap model){
        model.addAttribute("courses", courseService.findAll());

        return "inicio.html";
    }

}
