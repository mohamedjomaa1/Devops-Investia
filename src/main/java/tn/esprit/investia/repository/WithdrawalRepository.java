package tn.esprit.investia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.investia.entities.Withdrawal;

import java.util.List;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByUserId(Long userId);
}
