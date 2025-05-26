package tn.esprit.investia.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger; // Import Logger
import org.slf4j.LoggerFactory; // Import LoggerFactory
import org.springframework.stereotype.Service;
import tn.esprit.investia.dto.CoinGeckoMarketDTO; // Importe le DTO
import tn.esprit.investia.entities.CryptoCurrency; // Importe l'Entité
import tn.esprit.investia.repository.CryptoCurrencyRepository;

import java.util.ArrayList; // Import ArrayList
import java.util.List;

@Service
@RequiredArgsConstructor // Utilise Lombok pour injecter les dépendances final
public class DataPipelineService {

    private static final Logger logger = LoggerFactory.getLogger(DataPipelineService.class); // Ajout du logger

    private final tn.esprit.investia.services.CoinGeckoService coinGeckoService; // Injection via Lombok
    private final CryptoCurrencyRepository cryptoCurrencyRepository; // Injection via Lombok

    /**
     * Récupère les top cryptomonnaies depuis CoinGecko et les sauvegarde
     * dans la base de données si elles n'existent pas déjà (basé sur le nom).
     * @param limit Le nombre de cryptos à récupérer.
     */
    // @Scheduled(...) // Décommente si tu veux que ça s'exécute périodiquement
    public void fetchAndStoreTopCryptocurrencies(int limit) {
        logger.info("Début du pipeline: Récupération du Top {} cryptos...", limit);
        List<CoinGeckoMarketDTO> topCryptosDTOs = coinGeckoService.getTopCryptocurrencies(limit);

        if (topCryptosDTOs == null || topCryptosDTOs.isEmpty()) {
            logger.warn("Pipeline: Aucune donnée reçue de CoinGeckoService. Arrêt du traitement.");
            return;
        }

        logger.info("Pipeline: Reçu {} DTOs. Vérification et sauvegarde...", topCryptosDTOs.size());
        List<CryptoCurrency> entitiesToSave = new ArrayList<>();

        for (CoinGeckoMarketDTO dto : topCryptosDTOs) {
            // Vérifie par nom si la crypto existe déjà
            // Note: Vérifier par 'symbol' serait peut-être plus robuste si les noms peuvent avoir des variations
            if (!cryptoCurrencyRepository.existsByName(dto.getName())) {
                logger.debug("Pipeline: Crypto '{}' n'existe pas, préparation pour sauvegarde.", dto.getName());
                // === MAPING DTO vers ENTITÉ ===
                CryptoCurrency entity = new CryptoCurrency();

                // Mappe les champs que tu veux sauvegarder dans ta base de données
                // Utilise les getters du DTO et les setters de l'Entité
                entity.setName(dto.getName());
                entity.setSymbol(dto.getSymbol()); // Sauvegarde aussi le symbole
                entity.setImage(dto.getImage()); // Sauvegarde l'URL de l'image

                // Mappe les champs numériques en faisant attention aux types
                // (Le DTO utilise BigDecimal/Integer/Double, l'Entité utilise Long/Integer/Double)
                entity.setCurrentPrice(dto.getCurrentPrice() != null ? dto.getCurrentPrice().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setMarketCap(dto.getMarketCap() != null ? dto.getMarketCap().longValue() : null); // DTO BigDecimal -> Entité Long
                entity.setMarketCapRank(dto.getMarketCapRank()); // DTO Integer -> Entité Integer (ok)
                entity.setFullyDilutedValuation(dto.getFullyDilutedValuation() != null ? dto.getFullyDilutedValuation().longValue() : null); // DTO BigDecimal -> Entité Long
                entity.setTotalVolume(dto.getTotalVolume() != null ? dto.getTotalVolume().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setHigh24h(dto.getHigh24h() != null ? dto.getHigh24h().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setLow24h(dto.getLow24h() != null ? dto.getLow24h().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setPriceChange24h(dto.getPriceChange24h() != null ? dto.getPriceChange24h().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setPriceChangePercentage24h(dto.getPriceChangePercentage24h()); // DTO Double -> Entité Double (ok)
                entity.setMarketCapChange24h(dto.getMarketCapChange24h() != null ? dto.getMarketCapChange24h().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setMarketCapChangePercentage24h(dto.getMarketCapChangePercentage24h());// DTO Double -> Entité Double (ok)
                entity.setCirculatingSupply(dto.getCirculatingSupply() != null ? dto.getCirculatingSupply().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setTotalSupply(dto.getTotalSupply() != null ? dto.getTotalSupply().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setMaxSupply(dto.getMaxSupply() != null ? dto.getMaxSupply().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setAth(dto.getAth() != null ? dto.getAth().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setAthChangePercentage(dto.getAthChangePercentage()); // DTO Double -> Entité Double (ok)
                entity.setAthDate(dto.getAthDate()); // DTO OffsetDateTime -> Entité OffsetDateTime (ok)
                entity.setAtl(dto.getAtl() != null ? dto.getAtl().doubleValue() : null); // DTO BigDecimal -> Entité Double
                entity.setAtlChangePercentage(dto.getAtlChangePercentage()); // DTO Double -> Entité Double (ok)
                entity.setAtlDate(dto.getAtlDate()); // DTO OffsetDateTime -> Entité OffsetDateTime (ok)

                // Ajoute l'entité mappée à la liste à sauvegarder
                entitiesToSave.add(entity);
            } else {
                logger.debug("Pipeline: Crypto '{}' existe déjà, ignorée.", dto.getName());
            }
        }

        // Sauvegarde toutes les nouvelles entités en une seule fois (plus performant)
        if (!entitiesToSave.isEmpty()) {
            logger.info("Pipeline: Sauvegarde de {} nouvelle(s) cryptomonnaie(s)...", entitiesToSave.size());
            try {
                cryptoCurrencyRepository.saveAll(entitiesToSave);
                logger.info("Pipeline: Sauvegarde terminée avec succès.");
            } catch (Exception e) {
                logger.error("Pipeline: Erreur lors de la sauvegarde des entités CryptoCurrency.", e);
                // Gérer l'erreur de sauvegarde si nécessaire
            }
        } else {
            logger.info("Pipeline: Aucune nouvelle cryptomonnaie à sauvegarder.");
        }
        logger.info("Fin du pipeline.");
    }

    // Méthode pour vérifier si le repo contient déjà par nom (si elle n'existe pas)
    // Assure-toi que cette méthode existe dans ton CryptoCurrencyRepository
    // Si elle n'existe pas, crée-la dans l'interface CryptoCurrencyRepository :
    // boolean existsByName(String name);
}

// === Dans CryptoCurrencyRepository.java ===
// package tn.esprit.investia.repository;
// import org.springframework.data.jpa.repository.JpaRepository;
// import tn.esprit.investia.entities.CryptoCurrency;
// import java.util.Optional; // Pour findBy...
//
// public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {
//     // Ajoute cette méthode si elle manque :
//     boolean existsByName(String name);
//
//     // Optionnel : utile pour mettre à jour au lieu de juste vérifier l'existence
//     // Optional<CryptoCurrency> findByName(String name);
//     // Optional<CryptoCurrency> findBySymbol(String symbol);
// }
// =======================================