package tn.esprit.investia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.investia.dto.UserDTO;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.services.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/User")
public class UserRestController {

    @Autowired
    UserService userService;


    @PostMapping("/add-User")
    @ResponseBody
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.addUser(userDTO);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    // TO DO ROUTE LIST USER
    public List<User> getUser() {
        return userService.getUser();
    }

    @DeleteMapping("/{id}")
    // TO DO ROUTE DELETE USER
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping
    // TO DO ROUTE UPDATE USER
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}