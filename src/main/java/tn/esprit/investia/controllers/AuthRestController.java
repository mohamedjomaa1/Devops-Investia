package tn.esprit.investia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.investia.dto.LoginDto;
import tn.esprit.investia.dto.RegisterDto;
import tn.esprit.investia.entities.Role;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.repository.RoleRepository;
import tn.esprit.investia.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.util.Collections;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200") //Angular
//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8088"})
@RestController
@RequestMapping("/auth")
public class AuthRestController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthRestController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpSession session) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        try {
            User user = userRepository.findByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("loggedInUser", username);
                return ResponseEntity.ok("User logged in successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

        if (userRepository.existsByUserName(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is already in use!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUserName(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        //  Extract the Role object from the Optional
        Optional<Role> roleOptional = Optional.ofNullable(roleRepository.findByName("Investor"));
        if (!roleOptional.isPresent()) {
            return new ResponseEntity<>("Error: Role 'Investor' not found.", HttpStatus.INTERNAL_SERVER_ERROR); // Better error handling
        }
        Role role = roleOptional.get();

        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        String user = (String) session.getAttribute("loggedInUser");
        if (user != null) {
            return ResponseEntity.ok(Collections.singletonMap("username", user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
    }
}
