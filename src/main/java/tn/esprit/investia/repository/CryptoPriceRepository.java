package tn.esprit.investia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.investia.entities.CryptoPrice;

@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, String> {
}