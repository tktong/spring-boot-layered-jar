package me.poorlifechoices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MyController {
    @GetMapping("/")
    public String helloWorld() {
        return "Hello world";
    }
}
