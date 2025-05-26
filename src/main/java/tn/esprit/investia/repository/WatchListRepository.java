package tn.esprit.investia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.investia.entities.Watchlist;

public interface WatchListRepository extends JpaRepository<Watchlist, Long> {
    Watchlist findByUserId(Long userId);

}
