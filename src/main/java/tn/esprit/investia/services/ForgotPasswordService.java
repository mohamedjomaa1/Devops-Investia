package tn.esprit.investia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.investia.dto.ForgotPasswordDto;
import tn.esprit.investia.dto.ResetPasswordDto;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.repository.UserRepository;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public void storeResetToken(String email, String token) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setResetToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found for email: " + email);
        }
    }

    public void sendResetPasswordEmail(String email, String token) {
        String resetPasswordUrl = "http://example.com/reset-password?token=" + token;
        String subject = "Reset your password";
        String body = "Click the link below to reset your password:\n" + resetPasswordUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mohamed.jomaa@esprim.tn");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token);
        if (user == null) {
            throw new RuntimeException("Invalid reset token");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);
    }

}

