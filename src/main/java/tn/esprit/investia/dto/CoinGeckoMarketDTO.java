package tn.esprit.investia.dto; // Ou un autre package

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal; // Utilise BigDecimal pour la précision
import java.time.OffsetDateTime; // Garde OffsetDateTime si l'API le renvoie

// Pas d'annotation @Entity ou @Id ici, c'est un simple DTO
@JsonIgnoreProperties(ignoreUnknown = true) // Très important !
public class CoinGeckoMarketDTO {

    // === Champs correspondant STRICTEMENT au JSON de CoinGecko ===

    @JsonProperty("id")
    private String id; // C'est un String dans l'API CoinGecko

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("image")
    private String image;

    @JsonProperty("current_price")
    private BigDecimal currentPrice;

    @JsonProperty("market_cap")
    private BigDecimal marketCap; // Utilise BigDecimal pour les grands nombres

    @JsonProperty("market_cap_rank")
    private Integer marketCapRank;

    @JsonProperty("fully_diluted_valuation")
    private BigDecimal fullyDilutedValuation; // Utilise BigDecimal

    @JsonProperty("total_volume")
    private BigDecimal totalVolume; // Utilise BigDecimal

    @JsonProperty("high_24h")
    private BigDecimal high24h;

    @JsonProperty("low_24h")
    private BigDecimal low24h;

    @JsonProperty("price_change_24h")
    private BigDecimal priceChange24h;

    @JsonProperty("price_change_percentage_24h")
    private Double priceChangePercentage24h; // Double ok pour %

    @JsonProperty("market_cap_change_24h")
    private BigDecimal marketCapChange24h;

    @JsonProperty("market_cap_change_percentage_24h")
    private Double marketCapChangePercentage24h; // Double ok pour %

    @JsonProperty("circulating_supply")
    private BigDecimal circulatingSupply;

    @JsonProperty("total_supply")
    private BigDecimal totalSupply;

    @JsonProperty("max_supply")
    private BigDecimal maxSupply;

    @JsonProperty("ath")
    private BigDecimal ath;

    @JsonProperty("ath_change_percentage")
    private Double athChangePercentage;

    @JsonProperty("ath_date")
    private OffsetDateTime athDate; // Garde si l'API renvoie bien ce format

    @JsonProperty("atl")
    private BigDecimal atl;

    @JsonProperty("atl_change_percentage")
    private Double atlChangePercentage;

    @JsonProperty("atl_date")
    private OffsetDateTime atlDate; // Garde si l'API renvoie bien ce format

    // === Getters et Setters pour TOUS ces champs ===
    // Génère-les avec ton IDE

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
    public BigDecimal getMarketCap() { return marketCap; }
    public void setMarketCap(BigDecimal marketCap) { this.marketCap = marketCap; }
    public Integer getMarketCapRank() { return marketCapRank; }
    public void setMarketCapRank(Integer marketCapRank) { this.marketCapRank = marketCapRank; }
    public BigDecimal getFullyDilutedValuation() { return fullyDilutedValuation; }
    public void setFullyDilutedValuation(BigDecimal fullyDilutedValuation) { this.fullyDilutedValuation = fullyDilutedValuation; }
    public BigDecimal getTotalVolume() { return totalVolume; }
    public void setTotalVolume(BigDecimal totalVolume) { this.totalVolume = totalVolume; }
    public BigDecimal getHigh24h() { return high24h; }
    public void setHigh24h(BigDecimal high24h) { this.high24h = high24h; }
    public BigDecimal getLow24h() { return low24h; }
    public void setLow24h(BigDecimal low24h) { this.low24h = low24h; }
    public BigDecimal getPriceChange24h() { return priceChange24h; }
    public void setPriceChange24h(BigDecimal priceChange24h) { this.priceChange24h = priceChange24h; }
    public Double getPriceChangePercentage24h() { return priceChangePercentage24h; }
    public void setPriceChangePercentage24h(Double priceChangePercentage24h) { this.priceChangePercentage24h = priceChangePercentage24h; }
    public BigDecimal getMarketCapChange24h() { return marketCapChange24h; }
    public void setMarketCapChange24h(BigDecimal marketCapChange24h) { this.marketCapChange24h = marketCapChange24h; }
    public Double getMarketCapChangePercentage24h() { return marketCapChangePercentage24h; }
    public void setMarketCapChangePercentage24h(Double marketCapChangePercentage24h) { this.marketCapChangePercentage24h = marketCapChangePercentage24h; }
    public BigDecimal getCirculatingSupply() { return circulatingSupply; }
    public void setCirculatingSupply(BigDecimal circulatingSupply) { this.circulatingSupply = circulatingSupply; }
    public BigDecimal getTotalSupply() { return totalSupply; }
    public void setTotalSupply(BigDecimal totalSupply) { this.totalSupply = totalSupply; }
    public BigDecimal getMaxSupply() { return maxSupply; }
    public void setMaxSupply(BigDecimal maxSupply) { this.maxSupply = maxSupply; }
    public BigDecimal getAth() { return ath; }
    public void setAth(BigDecimal ath) { this.ath = ath; }
    public Double getAthChangePercentage() { return athChangePercentage; }
    public void setAthChangePercentage(Double athChangePercentage) { this.athChangePercentage = athChangePercentage; }
    public OffsetDateTime getAthDate() { return athDate; }
    public void setAthDate(OffsetDateTime athDate) { this.athDate = athDate; }
    public BigDecimal getAtl() { return atl; }
    public void setAtl(BigDecimal atl) { this.atl = atl; }
    public Double getAtlChangePercentage() { return atlChangePercentage; }
    public void setAtlChangePercentage(Double atlChangePercentage) { this.atlChangePercentage = atlChangePercentage; }
    public OffsetDateTime getAtlDate() { return atlDate; }
    public void setAtlDate(OffsetDateTime atlDate) { this.atlDate = atlDate; }

    // Constructeur vide requis par Jackson
    public CoinGeckoMarketDTO() { }
}