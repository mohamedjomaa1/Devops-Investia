package tn.esprit.investia.services;

import tn.esprit.investia.entities.Order;
import tn.esprit.investia.entities.User;
import tn.esprit.investia.entities.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long money);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user);
}
