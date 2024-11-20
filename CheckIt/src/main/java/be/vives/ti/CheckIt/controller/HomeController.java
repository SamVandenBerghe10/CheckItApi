package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.dao.model.Project;
import be.vives.ti.CheckIt.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class HomeController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String welcome() {
        return "<h1>CheckIt api!</h1>";
    }
}
