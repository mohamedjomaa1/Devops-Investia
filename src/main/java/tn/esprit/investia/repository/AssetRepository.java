package tn.esprit.investia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.investia.entities.Asset;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByUserId(Long userId);
    Asset findByUserIdAndCoinId(Long userId, String coinId);
}
