package tn.esprit.investia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.investia.dto.ForgotPasswordDto;
import tn.esprit.investia.dto.ResetPasswordDto;
import tn.esprit.investia.services.ForgotPasswordService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/pwd")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        String email = forgotPasswordDto.getEmail();
        String token = forgotPasswordService.generateToken();
        forgotPasswordService.storeResetToken(email, token);
        forgotPasswordService.sendResetPasswordEmail(email, token);
        return ResponseEntity.ok("Reset password email sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        String token = resetPasswordDto.getToken();
        String newPassword = resetPasswordDto.getNewPassword();
        forgotPasswordService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password has been reset.");
    }

}

