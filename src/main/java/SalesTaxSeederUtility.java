import java.util.List;

public class SalesTaxSeederUtility {
    private final SalesTaxFileManager salesTaxFileManager;
    private final SalesTaxMapper salesTaxMapper;
    private final SalesTaxDAO salesTaxDAO;

    public SalesTaxSeederUtility(final SalesTaxFileManager salesTaxFileManager, final SalesTaxMapper salesTaxMapper, final SalesTaxDAO salesTaxDAO) {
        this.salesTaxFileManager = salesTaxFileManager;
        this.salesTaxMapper = salesTaxMapper;
        this.salesTaxDAO = salesTaxDAO;
    }

    public void seedData() {
        //If we've already seeded the data into our database, no need to do it again.
        if (salesTaxDAO.getTotalNumber() > 0) {
            return;
        }

        String[] fileNames = salesTaxFileManager.getTaxFileNames();

        //The file name should not be null and we should have 52, one tax file for each state.
        if (fileNames == null || fileNames.length < 52) {
            return;
        }

        for (String fileName : fileNames) {
            List<String> rawData = salesTaxFileManager.getSalesTaxData(fileName);

            if (rawData != null && !rawData.isEmpty()) {
                for (String rowContent : rawData) {
                    SalesTaxDBModel dbModel = salesTaxMapper.map(rowContent);

                    if (dbModel != null) {
                        salesTaxDAO.create(dbModel);
                    }

                }
            }
        }
    }

    public SalesTaxDBModel getSalesTaxData(final String zipCode) {
        return salesTaxDAO.readByZipCode(zipCode);
    }
}
