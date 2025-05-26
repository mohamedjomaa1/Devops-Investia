package tn.esprit.investia.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Coin implements Serializable {
    @Id
    @JsonProperty("id")
    private String id;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image")
    private String image;
    @JsonProperty("current_price")
    private double currentPrice;
    @JsonProperty("market_cap")
    private double marketCap;
    @JsonProperty("market_cap_rank")
    private int marketCapRank;
    @JsonProperty("fully_diluted_valuation")
    private double fullyDilutedValuation;
    @JsonProperty("total_volume")
    private double totalVolume;
    @JsonProperty("high_24h")
    private double high24h;
    @JsonProperty("low_24h")
    private double low24h;
    @JsonProperty("price_change_24h")
    private double priceChange24h;
    @JsonProperty("price_change_percentage_24h")
    private double priceChangePercentage24h;
    @JsonProperty("market_cap_change_24h")
    private double marketCapChange24h;
    @JsonProperty("market_cap_change_percentage_24h")
    private double marketCapChangePercentage24h;
    @JsonProperty("circulating_supply")
    private double circulatingSupply;
    @JsonProperty("total_supply")
    private double totalSupply;
    @JsonProperty("max_supply")
    private double maxSupply;
    @JsonProperty("ath")
    private double ath;
    @JsonProperty("ath_change_percentage")
    private double athChangePercentage;
    @JsonProperty("ath_date")
    private Date athDate;
    @JsonProperty("atl")
    private double atl;
    @JsonProperty("atl_change_percentage")
    private double atlChangePercentage;
    @JsonProperty("atl_date")
    private Date atlDate;
    @Embedded
    @JsonProperty("roi")
    private Roi roi;
    @JsonProperty("last_updated")
    private Date lastUpdated;

}
