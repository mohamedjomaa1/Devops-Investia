package tn.esprit.investia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.investia.dto.CoinGeckoMarketDTO;
import tn.esprit.investia.entities.CryptoPrice;
import tn.esprit.investia.repository.CryptoPriceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CryptoPriceService {

    private final CryptoPriceRepository cryptoPriceRepository;
    private final tn.esprit.investia.services.CoinGeckoService coinGeckoService;

    @Autowired
    public CryptoPriceService(CryptoPriceRepository cryptoPriceRepository, tn.esprit.investia.services.CoinGeckoService coinGeckoService) {
        this.cryptoPriceRepository = cryptoPriceRepository;
        this.coinGeckoService = coinGeckoService;
    }

    public void saveCryptoPrice(String cryptoSymbol, String currency, BigDecimal price) {
        CryptoPrice cryptoPrice = new CryptoPrice();
        cryptoPrice.setCryptoSymbol(cryptoSymbol);
        cryptoPrice.setCurrency(currency);
        cryptoPrice.setPrice(price);

        cryptoPrice.setId(generateId(cryptoSymbol, currency));
        cryptoPrice.setTimestamp(LocalDateTime.now());

        cryptoPriceRepository.save(cryptoPrice);
        System.out.println("CryptoPrice enregistré : " + cryptoPrice.getId());
    }

    @Scheduled(fixedRate = 60000) // Exécute toutes les minutes (60000 millisecondes)
    public void updateCryptoPrices() {
        List<CoinGeckoMarketDTO> cryptocurrencies = coinGeckoService.getTopCryptocurrencies(10); // Récupère les 10 premières cryptomonnaies
        for (CoinGeckoMarketDTO crypto : cryptocurrencies) {
            BigDecimal price = crypto.getCurrentPrice(); // Récupère le prix depuis l'objet CryptoCurrency
            if (price != null) {
                saveCryptoPrice(crypto.getSymbol(), "USD", price);
            } else {
                System.err.println("Impossible de récupérer le prix pour " + crypto.getSymbol());
            }
        }
    }

    private String generateId(String cryptoSymbol, String currency) {
        return cryptoSymbol + "-" + currency + "-" + LocalDateTime.now();
    }
}