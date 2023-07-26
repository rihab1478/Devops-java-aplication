package tn.esprit.spring.nemp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class NemPApplication {

    @GetMapping
    public String message()
    {
        return "welcome to my DevSecOps application ";
    }

    public static void main(String[] args) {
        SpringApplication.run(NemPApplication.class, args);
    }

}
