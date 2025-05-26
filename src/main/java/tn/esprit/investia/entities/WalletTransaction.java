package tn.esprit.investia.entities;

import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.investia.domain.WalletTransactionType;

import java.time.LocalDate;


@Data
@Entity
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Wallet wallet;
    private WalletTransactionType type;
    private LocalDate date;
    private String transferId;
    private String purpose;
    private Long amount;
}
