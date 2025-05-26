package tn.esprit.investia.entities;

import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.investia.domain.WithdrawalStatus;

import java.time.LocalDateTime;

@Data
@Entity
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WithdrawalStatus status;

    private Long amount;
    @ManyToOne
    private User user;
    private LocalDateTime date=LocalDateTime.now();
}
