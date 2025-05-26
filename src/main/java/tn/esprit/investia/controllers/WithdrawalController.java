package tn.esprit.investia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.entities.Wallet;
import tn.esprit.investia.entities.Withdrawal;
import tn.esprit.investia.services.IUserInterface;
import tn.esprit.investia.services.WalletService;
import tn.esprit.investia.services.WithdrawalService;

import java.util.List;

@RestController
public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;
    @Autowired
    private IUserInterface userService;
    @Autowired
    private WalletService walletService;
    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(@PathVariable Long amount, @RequestHeader("Authorization") String email ) throws Exception {
        User user = userService.getUserByEmail(email);
        Wallet userWallet = walletService.getUserWallet(user);
        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());
        return new ResponseEntity<>(withdrawal, HttpStatus.ACCEPTED);
    }
    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(@PathVariable Long id, @PathVariable boolean accept,@RequestHeader("Authorization") String email) throws Exception {
        User user = userService.getUserByEmail(email);
        Withdrawal withdrawal = withdrawalService.procedWithwithdrawal(id, accept);
        Wallet userWallet = walletService.getUserWallet(user);
        if (!accept) {
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getUserWithdrawalHistory(@RequestHeader("Authorization") String email) {
        User user = userService.getUserByEmail(email);
        List<Withdrawal> withdrawalList = withdrawalService.getUsersWithdrawalHistory(user);
        return new ResponseEntity<>(withdrawalList, HttpStatus.OK);
    }
    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(@RequestHeader("Authorization") String email) {
        List<Withdrawal> withdrawalList = withdrawalService.getAllWithdrawalRequest();
        return new ResponseEntity<>(withdrawalList, HttpStatus.OK);
    }
}
