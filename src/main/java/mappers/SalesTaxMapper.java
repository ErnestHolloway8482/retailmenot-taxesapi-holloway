package mappers;

import models.database.SalesTaxDBModel;

/**
 * @author ernestholloway
 * <p>
 * This mapper class is responsible to translating a row from the .csv file for the text based tax information
 * into a corresponding database model.
 */
public class SalesTaxMapper {
    private static final int CSV_ROW_DATA_LENGTH = 9;

    /**
     * Converts a row of .csv data into a {@link SalesTaxDBModel} so that the content is easily searchable by zipcode.
     *
     * @param rowContent is the .csv representation of a row of sales tax information.
     * @return {@link SalesTaxDBModel} if the data is successfully parsed, false otherwise.
     */
    public SalesTaxDBModel map(final String rowContent) {
        String[] csvData = rowContent != null ? rowContent.split(",") : null;

        if (csvData == null || csvData.length != CSV_ROW_DATA_LENGTH) {
            return null;
        } else {
            //This is the order the data should be parsed
            //State,ZipCode,TaxRegionName,StateRate,EstimatedCombinedRate,EstimatedCountyRate,EstimatedCityRate,EstimatedSpecialRate,RiskLevel

            SalesTaxDBModel model = new SalesTaxDBModel();
            model.setState(csvData[0]);
            model.setZipCode(csvData[1]);
            model.setTaxRegionName(csvData[2]);
            model.setStateRate(parseFloat(csvData[3]));
            model.setEstimatedCombinedRate(parseFloat(csvData[4]));
            model.setEstimatedCountyRate(parseFloat(csvData[5]));
            model.setEstimatedCityRate(parseFloat(csvData[6]));
            model.setEstimatedSpecialRate(parseFloat(csvData[7]));
            model.setRiskLevel(parseInteger(csvData[8]));

            return model;
        }
    }

    /**
     * Converts a string value to a float.
     *
     * @param floatValue is the string value of the number.
     * @return the float if successfully parsed, -1 otherwise.
     */
    public float parseFloat(final String floatValue) {
        try {
            return Float.parseFloat(floatValue);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Converts a string value to an integer.
     *
     * @param integerValue is the string value of the number.
     * @return the integer if successfully parsed, -1 otherwise.
     */
    public int parseInteger(final String integerValue) {
        try {
            return Integer.parseInt(integerValue);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
