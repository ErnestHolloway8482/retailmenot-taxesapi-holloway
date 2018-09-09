import java.text.ParseException;

public class SalesTaxMapper {
    public SalesTaxDBModel map(final String rowContent) {
        String[] csvData = rowContent!=null ? rowContent.split(",") : null;

        if (csvData == null || csvData.length != 9) {
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

    public float parseFloat(final String floatValue) {
        try {
            return Float.parseFloat(floatValue);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int parseInteger(final String integerValue) {
        try {
            return Integer.parseInt(integerValue);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
