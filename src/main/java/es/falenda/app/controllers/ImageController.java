package es.falenda.app.controllers;

import es.falenda.app.models.Student;
import es.falenda.app.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private StudentService studentService;
    @GetMapping("showStudent/{id}")
    public ResponseEntity<byte[]> imageStudent(@PathVariable String id){
        Student student = studentService.findById(id);

        byte[] image = student.getImage().getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

}
