package models.database;

import javax.persistence.Entity;
import java.util.UUID;

/**
 * @author ernestholloway
 * <p>
 * This is a database model that represents the sales tax information avaialble for a given zip code.
 * This information will be stored via an embedded database that is searchable by a public exposed API later.
 */
@Entity
public class SalesTaxDBModel {
    private String uuid;
    private String state;
    private String zipCode;
    private String taxRegionName;
    private float stateRate;
    private float estimatedCombinedRate;
    private float estimatedCountyRate;
    private float estimatedCityRate;
    private float estimatedSpecialRate;
    private int riskLevel;

    public SalesTaxDBModel() {
        uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTaxRegionName() {
        return taxRegionName;
    }

    public void setTaxRegionName(String taxRegionName) {
        this.taxRegionName = taxRegionName;
    }

    public float getStateRate() {
        return stateRate;
    }

    public void setStateRate(float stateRate) {
        this.stateRate = stateRate;
    }

    public float getEstimatedCombinedRate() {
        return estimatedCombinedRate;
    }

    public void setEstimatedCombinedRate(float estimatedCombinedRate) {
        this.estimatedCombinedRate = estimatedCombinedRate;
    }

    public float getEstimatedCountyRate() {
        return estimatedCountyRate;
    }

    public void setEstimatedCountyRate(float estimatedCountyRate) {
        this.estimatedCountyRate = estimatedCountyRate;
    }

    public float getEstimatedCityRate() {
        return estimatedCityRate;
    }

    public void setEstimatedCityRate(float estimatedCityRate) {
        this.estimatedCityRate = estimatedCityRate;
    }

    public float getEstimatedSpecialRate() {
        return estimatedSpecialRate;
    }

    public void setEstimatedSpecialRate(float estimatedSpecialRate) {
        this.estimatedSpecialRate = estimatedSpecialRate;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(int riskLevel) {
        this.riskLevel = riskLevel;
    }
}
