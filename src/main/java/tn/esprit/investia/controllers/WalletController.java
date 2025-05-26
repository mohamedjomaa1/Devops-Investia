package tn.esprit.investia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.entities.Wallet;
import tn.esprit.investia.entities.WalletTransaction;
import tn.esprit.investia.services.IUserInterface;
import tn.esprit.investia.services.WalletService;

@RestController
public class WalletController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private IUserInterface userService;


    //public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) {
        //User user = userService.findUserProfileByJwt(jwt);
    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String email) {
        User user = userService.getUserByEmail(email);
        Wallet wallet = walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization") String email,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req) throws Exception {
        User senderUser = userService.getUserByEmail(email);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet, req.getAmount());
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
/*
    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization") String email,
            @PathVariable Long orderId) throws Exception {
        User user = userService.getUserByEmail(email);
        Order order = orderService.getOrderById(orderId);
        Wallet wallet = walletService.payOrderPayment(order, user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
*/
}
