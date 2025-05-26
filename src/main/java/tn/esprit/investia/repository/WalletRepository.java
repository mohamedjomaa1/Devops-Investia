package tn.esprit.investia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.investia.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long userId);
}
