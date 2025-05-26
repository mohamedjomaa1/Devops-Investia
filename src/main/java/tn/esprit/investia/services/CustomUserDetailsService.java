package tn.esprit.investia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private DataSource dataSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String query = "SELECT * FROM users WHERE username = ?";

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Map<String, Object> userRow = jdbcTemplate.queryForMap(query, username);
            String password = (String) userRow.get("password");
            return User.withUsername(username)
                    .password(password)
                    .roles("USER") // Tu peux ajouter d'autres rôles si nécessaire
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
    }
}
