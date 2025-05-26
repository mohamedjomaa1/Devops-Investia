package tn.esprit.investia.services;

import tn.esprit.investia.entities.Coin;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.entities.Watchlist;

public interface WatchListService {
    Watchlist findUserWatchList(Long userId) throws Exception;
    Watchlist createWatchList(User user);
    Watchlist findById(Long id) throws Exception;
    Coin addItemToWatchList(Coin coin, User user) throws Exception;
}
