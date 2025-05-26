package tn.esprit.investia.dto;

import lombok.Data;


@Data
public class ForgotPasswordDto {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

