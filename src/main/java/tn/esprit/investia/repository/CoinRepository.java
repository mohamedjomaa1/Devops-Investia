package tn.esprit.investia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.investia.entities.Coin;

public interface CoinRepository extends JpaRepository<Coin, String> {

}
