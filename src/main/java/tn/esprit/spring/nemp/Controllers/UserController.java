package tn.esprit.spring.nemp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.nemp.Entities.User;
import tn.esprit.spring.nemp.Services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }


    @PostMapping("/add")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public String  deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "user deleted succefully";
    }
    @GetMapping("/index")
    public  String index()

    {
        return "hi and welcome to my Devsecops application video test2 ";
    }

}
