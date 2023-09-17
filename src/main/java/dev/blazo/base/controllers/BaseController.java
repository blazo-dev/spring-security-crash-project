package dev.blazo.base.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class BaseController {

    @GetMapping("/hello")
    public String showBase() {
        return "Hello World from Base Project!";
    }

    @GetMapping("/admin")
    public String showAdmin() {
        return "Hello Admin!";
    }
}
