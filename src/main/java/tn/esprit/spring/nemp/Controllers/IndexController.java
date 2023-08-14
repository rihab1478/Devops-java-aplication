package tn.esprit.spring.nemp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @GetMapping("/api/index")
    @ResponseBody
    public  String index()
    {
        return "index2";
    }
}
