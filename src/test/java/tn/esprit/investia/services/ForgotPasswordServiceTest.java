package tn.esprit.investia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.repository.UserRepository;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForgotPasswordServiceTest {

    @InjectMocks
    private ForgotPasswordService forgotPasswordService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JavaMailSender mailSender;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("oldPassword");
    }

    @Test
    void testStoreResetToken_UserFound_ShouldStoreToken() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        forgotPasswordService.storeResetToken("test@example.com", "reset-token");

        assertEquals("reset-token", user.getResetPasswordToken());
        verify(userRepository).save(user);
    }

    @Test
    void testStoreResetToken_UserNotFound_ShouldThrowException() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () ->
                forgotPasswordService.storeResetToken("notfound@example.com", "reset-token")
        );
    }

    @Test
    void testResetPassword_ValidToken_ShouldResetPasswordAndClearToken() {
        String token = "valid-token";
        when(userRepository.findByResetPasswordToken(token)).thenReturn(user);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        forgotPasswordService.resetPassword(token, "newPassword");

        assertEquals("encodedNewPassword", user.getPassword());
        assertNull(user.getResetPasswordToken());
        verify(userRepository).save(user);
    }

    @Test
    void testResetPassword_InvalidToken_ShouldThrowException() {
        when(userRepository.findByResetPasswordToken("invalid-token")).thenReturn(null);

        assertThrows(RuntimeException.class, () ->
                forgotPasswordService.resetPassword("invalid-token", "newPassword")
        );
    }
}
