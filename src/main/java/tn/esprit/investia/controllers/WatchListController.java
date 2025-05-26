package tn.esprit.investia.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.investia.entities.Coin;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.entities.Watchlist;
import tn.esprit.investia.services.CoinService;
import tn.esprit.investia.services.IUserInterface;
import tn.esprit.investia.services.WatchListService;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;
    @Autowired
    private IUserInterface userService;
    @Autowired
    private CoinService coinService;
    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchList(@RequestHeader("Authorization") String email) throws Exception {
        User user = userService.getUserByEmail(email);
        Watchlist watchlist = watchListService.findUserWatchList(user.getId());
        return ResponseEntity.ok(watchlist);
    }
    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchListById(@PathVariable Long watchlistId) throws Exception {
        Watchlist watchlist = watchListService.findById(watchlistId);
        return ResponseEntity.ok(watchlist);
    }
    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(@RequestHeader("Authorization") String email ,@PathVariable String coinId) throws Exception {
        User user = userService.getUserByEmail(email);
        Coin coin = coinService.findById(coinId);
       Coin addedCoin =  watchListService.addItemToWatchList(coin, user);
        return ResponseEntity.ok(addedCoin);
    }
}
