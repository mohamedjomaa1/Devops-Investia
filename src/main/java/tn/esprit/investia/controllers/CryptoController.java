package tn.esprit.investia.controllers; // Ton package

// Imports Spring Web standard
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.CrossOrigin; // Commenté car géré globalement

// Imports pour les annotations OpenAPI / Swagger 3
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema; // Import pour ArraySchema
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// Imports de tes classes
import tn.esprit.investia.dto.CoinGeckoMarketDTO; // Utilise le DTO
import tn.esprit.investia.services.CryptoService; // Package 'services'
import tn.esprit.investia.services.CoinGeckoService; // Package 'services'

// Imports Java standard et Logging
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/crypto") // Préfixe pour toutes les routes de ce contrôleur
@Tag(name = "Cryptocurrency API", description = "Endpoints pour récupérer les données de marché des cryptomonnaies")
// @CrossOrigin(origins = "http://localhost:4200") // Géré globalement dans SecurityConfig
public class CryptoController {

    private static final Logger logger = LoggerFactory.getLogger(CryptoController.class);

    private final CoinGeckoService coinGeckoService;
    private final CryptoService cryptoService;

    // Injection par constructeur
    public CryptoController(CoinGeckoService coinGeckoService, CryptoService cryptoService) {
        this.coinGeckoService = coinGeckoService;
        this.cryptoService = cryptoService;
    }

    /**
     * Endpoint pour récupérer la liste des top cryptomonnaies par capitalisation.
     */
    @GetMapping("/top")
    @Operation(summary = "Obtenir les Top N Cryptomonnaies par Market Cap",
            description = "Récupère une liste de DTOs contenant les informations de marché des N premières cryptomonnaies classées par capitalisation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des DTOs de marché récupérée avec succès",
                    content = { @Content(mediaType = "application/json",
                            // Décrit un tableau de CoinGeckoMarketDTO
                            array = @ArraySchema(schema = @Schema(implementation = CoinGeckoMarketDTO.class))) }),
            @ApiResponse(responseCode = "400", description = "Paramètre 'limit' invalide (par exemple, négatif)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erreur interne ou API CoinGecko indisponible", content = @Content)
    })
    public List<CoinGeckoMarketDTO> getTopCryptocurrencies(
            @Parameter(description = "Nombre maximum de cryptomonnaies à retourner.", example = "10", required = false)
            @RequestParam(defaultValue = "10") int limit) {
        logger.info("Requête reçue pour GET /api/crypto/top avec limit={}", limit);
        List<CoinGeckoMarketDTO> result = coinGeckoService.getTopCryptocurrencies(limit);
        logger.info("Retour de coinGeckoService.getTopCryptocurrencies pour /top: {} élément(s)", (result != null ? result.size() : "null"));
        return result;
    }

    /**
     * Endpoint pour récupérer les prix actuels de Bitcoin et Ethereum en USD/EUR.
     */
    // Dans CryptoController.java, pour la méthode getCurrentCryptoPrices()

    @GetMapping("/prices")
    @Operation(summary = "Obtenir les Prix Actuels BTC/ETH",
            description = "Retourne les prix courants de Bitcoin et Ethereum en USD et EUR via l'API simple de CoinGecko.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prix récupérés avec succès",
                    content = { @Content(mediaType = "application/json",
                            // === CORRECTION FINALE ICI ===
                            schema = @Schema(type = "object",
                                    // Utilise la constante enum pour TRUE
                                    additionalProperties = Schema.AdditionalPropertiesValue.TRUE,
                                    example = "{\"bitcoin\":{\"usd\":65000.0,\"eur\":60000.0},\"ethereum\":{\"usd\":3500.0,\"eur\":3200.0}}")) }),
            @ApiResponse(responseCode = "500", description = "Erreur interne ou API CoinGecko indisponible", content = @Content)
    })

    public Map<String, Map<String, Double>> getCurrentCryptoPrices() {
        logger.info("Requête reçue pour GET /api/crypto/prices");
        Map<String, Map<String, Double>> result = cryptoService.getCryptoPrices();
        logger.info("Retour de cryptoService.getCryptoPrices pour /prices: contient {} clé(s)", (result != null ? result.size() : "null"));
        return result;
    }

    // Ajoute ici d'autres endpoints et leurs annotations si besoin
}