package be.vives.ti.CheckIt.controller;

import be.vives.ti.CheckIt.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String welcome() {
        return "<h1>CheckIt api!</h1><a href='https://checkit-444817.ew.r.appspot.com/swagger-ui/index.html'>Swagger docs</a>";
    }
}
