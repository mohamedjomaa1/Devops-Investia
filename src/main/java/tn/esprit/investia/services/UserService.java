package tn.esprit.investia.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.investia.dto.UserDTO;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.repository.UserRepository;
import tn.esprit.investia.repository.RoleRepository;
import tn.esprit.investia.entities.Role;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class UserService implements IUserInterface {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    // New addUser method that takes the DTO
    public User addUser(UserDTO userDTO) {

        // Check if username already exists
        if (userRepository.existsByUserName(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username is taken!");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        User user = new User();
        user.setUserName(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        List<Role> roles = new ArrayList<>();
        if (userDTO.getRoles() != null) {
            for (String roleName : userDTO.getRoles()) {
                // Find the role by name (you might want to find by ID instead, see notes below)
                Role role = roleRepository.findByName(roleName);
                if (role != null) {
                    roles.add(role);
                } else {
                    // Handle the case where the role doesn't exist (e.g., throw an exception)
                    throw new IllegalArgumentException("Role '" + roleName + "' not found.");
                }
            }
        }
        user.setRoles(roles); // Set the roles on the user object

        return userRepository.save(user);
    }

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserByEmail(String email) {
        String hql = "FROM User u WHERE u.email = :email";
        Query<User> query = (Query<User>) entityManager.createQuery(hql, User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        } else if (users.size() == 1) {
            return users.get(0);
        } else {
            throw new RuntimeException("Multiple users found with the same email address");
        }
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

}