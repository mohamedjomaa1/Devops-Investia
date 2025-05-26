package tn.esprit.investia.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String username;
    private String password;
    private List<String> roles; // Or List<Long> if you prefer role IDs
    private String email;
}