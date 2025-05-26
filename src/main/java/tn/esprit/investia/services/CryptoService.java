package tn.esprit.investia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors; // Import pour Collectors

@Service
public class CryptoService {

    private static final Logger logger = LoggerFactory.getLogger(CryptoService.class);

    // Garder l'URL ici ou la mettre dans application.properties
    private final String COINGECKO_API_URL = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum&vs_currencies=usd,eur";

    private final RestTemplate restTemplate; // Dépendance injectée

    // Injecter via le constructeur
    public CryptoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Récupère les prix de Bitcoin et Ethereum depuis l'API CoinGecko
     * et s'assure que les valeurs numériques sont bien des Doubles.
     *
     * @return Une Map contenant les prix, ou une Map vide en cas d'erreur ou de réponse invalide.
     */
    public Map<String, Map<String, Double>> getCryptoPrices() {
        try {
            // 1. Appel API et récupération de la Map brute (peut contenir Integer ou Double)
            //    Utilisation de Map<?, ?> pour plus de flexibilité à la réception.
            Map<?, ?> rawPrices = restTemplate.getForObject(COINGECKO_API_URL, Map.class);

            // 2. Vérification de la réponse brute
            if (rawPrices != null && !rawPrices.isEmpty()) {
                logger.debug("Prix bruts récupérés avec succès depuis CoinGecko simple API.");

                // 3. Conversion explicite et sécurisée en Map<String, Map<String, Double>>
                @SuppressWarnings("unchecked") // Nécessaire pour le cast ci-dessous
                Map<String, Map<String, Object>> typedRawPrices = (Map<String, Map<String, Object>>) rawPrices;

                return typedRawPrices.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, // Clé externe (ex: "bitcoin")
                                outerEntry -> {
                                    // Vérifie si la valeur interne est bien une Map
                                    if (outerEntry.getValue() instanceof Map) {
                                        @SuppressWarnings("unchecked") // Nécessaire pour le cast interne
                                        Map<String, Object> innerMap = (Map<String, Object>) outerEntry.getValue();

                                        // Traite la map interne pour convertir les nombres en Double
                                        return innerMap.entrySet().stream()
                                                .filter(innerEntry -> innerEntry.getValue() instanceof Number) // Garde seulement les valeurs numériques
                                                .collect(Collectors.toMap(
                                                        Map.Entry::getKey, // Clé interne (ex: "usd")
                                                        innerEntry -> ((Number) innerEntry.getValue()).doubleValue() // Conversion en Double
                                                ));
                                    } else {
                                        // Si la valeur interne n'est pas une Map, retourne une map vide pour cette clé
                                        logger.warn("Structure de réponse inattendue pour la clé '{}'. Attendu : Map, Reçu : {}",
                                                outerEntry.getKey(), outerEntry.getValue() != null ? outerEntry.getValue().getClass().getName() : "null");
                                        return Collections.<String, Double>emptyMap();
                                    }
                                }
                        ));

            } else {
                logger.warn("L'API CoinGecko simple a retourné une réponse nulle ou vide.");
                return Collections.emptyMap();
            }
            // Gestion plus spécifique des exceptions potentielles
        } catch (RestClientException e) {
            logger.error("Erreur réseau ou HTTP lors de l'appel à l'API CoinGecko simple : {}", e.getMessage());
            return Collections.emptyMap();
        } catch (ClassCastException | NullPointerException e) {
            logger.error("Erreur lors de la conversion ou du traitement de la structure de la réponse API : {}", e.getMessage(), e);
            return Collections.emptyMap(); // Gérer l'erreur de structure/conversion
        } catch (Exception e) { // Capture les autres erreurs inattendues
            logger.error("Erreur inattendue lors de la récupération des prix : {}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }
}