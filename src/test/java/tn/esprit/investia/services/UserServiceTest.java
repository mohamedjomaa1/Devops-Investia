package tn.esprit.investia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import tn.esprit.investia.dto.UserDTO;
import tn.esprit.investia.entities.Role;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.repository.RoleRepository;
import tn.esprit.investia.repository.UserRepository;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private User user;
    private UserDTO userDTO;
    private Role role;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUserName("john");
        user.setEmail("john@example.com");
        user.setPassword("password");

        userDTO = new UserDTO();
        userDTO.setUsername("john");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("password");
        userDTO.setRoles(Collections.singletonList("USER"));

        role = new Role();
        role.setId(1L);
        role.setName("USER");
    }

    @Test
    void testAddUser_Success() {
        when(userRepository.existsByUserName(userDTO.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.addUser(userDTO);

        assertNotNull(savedUser);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testAddUser_UsernameExists_ShouldThrowException() {
        when(userRepository.existsByUserName(userDTO.getUsername())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                userService.addUser(userDTO)
        );
    }

    @Test
    void testAddUser_EmailExists_ShouldThrowException() {
        when(userRepository.existsByUserName(userDTO.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                userService.addUser(userDTO)
        );
    }

    @Test
    void testAddUser_RoleNotFound_ShouldThrowException() {
        when(userRepository.existsByUserName(userDTO.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                userService.addUser(userDTO)
        );
    }
}
