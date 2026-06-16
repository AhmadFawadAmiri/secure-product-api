package com.afa.demo0001;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHell(){
        return "Hello Spring!";
    }

    @GetMapping("/greet")
    public String greetUser(@RequestParam(value = "name", defaultValue = "Fawad") String name){
        return "Hello dear " + name;
    }
}
