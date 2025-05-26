package tn.esprit.investia.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    private String name;
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("image")
    private String image;

    @JsonProperty("current_price")
    private Double currentPrice;

    @JsonProperty("market_cap")
    private Long marketCap;

    @JsonProperty("market_cap_rank")
    private Integer marketCapRank;

    @JsonProperty("fully_diluted_valuation")
    private Long fullyDilutedValuation;

    @JsonProperty("total_volume")
    private Double totalVolume;

    @JsonProperty("high_24h")
    private Double high24h;

    @JsonProperty("low_24h")
    private Double low24h;

    @JsonProperty("price_change_24h")
    private Double priceChange24h;

    @JsonProperty("price_change_percentage_24h")
    private Double priceChangePercentage24h;

    @JsonProperty("market_cap_change_24h")
    private Double marketCapChange24h;

    @JsonProperty("market_cap_change_percentage_24h")
    private Double marketCapChangePercentage24h;

    @JsonProperty("circulating_supply")
    private Double circulatingSupply;

    @JsonProperty("total_supply")
    private Double totalSupply;

    @JsonProperty("max_supply")
    private Double maxSupply;

    @JsonProperty("ath")
    private Double ath;

    @JsonProperty("ath_change_percentage")
    private Double athChangePercentage;

    @JsonProperty("ath_date")
    private OffsetDateTime athDate;

    @JsonProperty("atl")
    private Double atl;

    @JsonProperty("atl_change_percentage")
    private Double atlChangePercentage;

    @JsonProperty("atl_date")
    private OffsetDateTime atlDate;
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }

    public Long getMarketCap() { return marketCap; }
    public void setMarketCap(Long marketCap) { this.marketCap = marketCap; }

    public Integer getMarketCapRank() { return marketCapRank; }
    public void setMarketCapRank(Integer marketCapRank) { this.marketCapRank = marketCapRank; }

    public Long getFullyDilutedValuation() { return fullyDilutedValuation; }
    public void setFullyDilutedValuation(Long fullyDilutedValuation) { this.fullyDilutedValuation = fullyDilutedValuation; }

    public Double getTotalVolume() { return totalVolume; }
    public void setTotalVolume(Double totalVolume) { this.totalVolume = totalVolume; }

    public Double getHigh24h() { return high24h; }
    public void setHigh24h(Double high24h) { this.high24h = high24h; }

    public Double getLow24h() { return low24h; }
    public void setLow24h(Double low24h) { this.low24h = low24h; }

    public Double getPriceChange24h() { return priceChange24h; }
    public void setPriceChange24h(Double priceChange24h) { this.priceChange24h = priceChange24h; }

    public Double getPriceChangePercentage24h() { return priceChangePercentage24h; }
    public void setPriceChangePercentage24h(Double priceChangePercentage24h) { this.priceChangePercentage24h = priceChangePercentage24h; }

    public Double getMarketCapChange24h() { return marketCapChange24h; }
    public void setMarketCapChange24h(Double marketCapChange24h) { this.marketCapChange24h = marketCapChange24h; }

    public Double getMarketCapChangePercentage24h() { return marketCapChangePercentage24h; }
    public void setMarketCapChangePercentage24h(Double marketCapChangePercentage24h) { this.marketCapChangePercentage24h = marketCapChangePercentage24h; }

    public Double getCirculatingSupply() { return circulatingSupply; }
    public void setCirculatingSupply(Double circulatingSupply) { this.circulatingSupply = circulatingSupply; }

    public Double getTotalSupply() { return totalSupply; }
    public void setTotalSupply(Double totalSupply) { this.totalSupply = totalSupply; }

    public Double getMaxSupply() { return maxSupply; }
    public void setMaxSupply(Double maxSupply) { this.maxSupply = maxSupply; }

    public Double getAth() { return ath; }
    public void setAth(Double ath) { this.ath = ath; }

    public Double getAthChangePercentage() { return athChangePercentage; }
    public void setAthChangePercentage(Double athChangePercentage) { this.athChangePercentage = athChangePercentage; }

    public OffsetDateTime getAthDate() { return athDate; }

    public void setAthDate(OffsetDateTime athDate) {
        this.athDate = athDate;
    }

    public Double getAtl() { return atl; }
    public void setAtl(Double atl) { this.atl = atl; }

    public Double getAtlChangePercentage() { return atlChangePercentage; }
    public void setAtlChangePercentage(Double atlChangePercentage) { this.atlChangePercentage = atlChangePercentage; }

    public OffsetDateTime getAtlDate() { return atlDate; }

    public void setAtlDate(OffsetDateTime atlDate) {
        this.atlDate = atlDate;
    }
}