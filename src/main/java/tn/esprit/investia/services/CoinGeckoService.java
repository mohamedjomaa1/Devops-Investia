package tn.esprit.investia.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
// Importe le nouveau DTO
import tn.esprit.investia.dto.CoinGeckoMarketDTO;
// Imports pour les exceptions et le Logger
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Collections;

@Service
public class CoinGeckoService {

    private static final Logger logger = LoggerFactory.getLogger(CoinGeckoService.class);

    @Value("${coingecko.base-url}")
    private String coingeckoBaseUrl;

    @Value("${coingecko.rate-limit-delay:1000}")
    private int rateLimitDelay;

    private final RestTemplate restTemplate;

    public CoinGeckoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Récupère les top cryptomonnaies depuis l'API CoinGecko /coins/markets.
     * @param limit Nombre de cryptomonnaies à récupérer.
     * @return Liste des CoinGeckoMarketDTO ou liste vide en cas d'échec.
     */
    // La signature de la méthode change pour retourner le DTO
    @Cacheable(value = "cryptocurrenciesDTO", key = "#limit") // Change le nom du cache si besoin
    public List<CoinGeckoMarketDTO> getTopCryptocurrencies(int limit) {
        if (coingeckoBaseUrl == null || coingeckoBaseUrl.trim().isEmpty()) {
            logger.error("L'URL de base de CoinGecko (coingecko.base-url) n'est pas configurée !");
            return Collections.emptyList();
        }
        String url = coingeckoBaseUrl + "/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=" + limit + "&page=1";
        logger.info("Appel de l'API CoinGecko : GET {}", url);

        try {
            if (rateLimitDelay > 0) {
                Thread.sleep(rateLimitDelay);
            }

            // Appel de l'API en spécifiant List<CoinGeckoMarketDTO> comme type attendu
            ResponseEntity<List<CoinGeckoMarketDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    // Utilise le DTO dans ParameterizedTypeReference
                    new ParameterizedTypeReference<List<CoinGeckoMarketDTO>>() {}
            );

            List<CoinGeckoMarketDTO> cryptoMarketData = response.getBody();
            if (response.getStatusCode().is2xxSuccessful() && cryptoMarketData != null) {
                logger.info("Récupération réussie de {} DTOs depuis CoinGecko.", cryptoMarketData.size());
                // logger.debug("Données DTO reçues: {}", cryptoMarketData);
                return cryptoMarketData; // Retourne la liste de DTOs
            } else {
                logger.warn("Réponse CoinGecko OK (Statut {}) mais corps de réponse null ou inattendu.", response.getStatusCode());
                return Collections.emptyList();
            }
        } catch (HttpClientErrorException e) {
            logger.warn("Erreur HTTP Client {} lors de l'appel à {}: {}", e.getStatusCode(), url, e.getResponseBodyAsString());
            return Collections.emptyList();
        } catch (HttpServerErrorException e) {
            logger.error("Erreur HTTP Serveur {} lors de l'appel à {}: {}", e.getStatusCode(), url, e.getResponseBodyAsString());
            return Collections.emptyList();
        } catch (ResourceAccessException e) {
            logger.error("Erreur d'accès réseau lors de l'appel à {}: {}", url, e.getMessage());
            return Collections.emptyList();
        } catch (InterruptedException e) {
            logger.warn("Thread interrompu pendant la pause pour rate limit.");
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        } catch (Exception e) {
            // Capture aussi les erreurs de désérialisation (qui étaient le problème initial)
            logger.error("Erreur inattendue (probablement désérialisation) lors de la récupération des cryptomonnaies depuis {}: {}", url, e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}