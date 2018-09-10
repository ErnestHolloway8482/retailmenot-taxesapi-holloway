package facades;

import daos.SalesTaxDAO;
import managers.ObjectDBManager;
import mappers.SalesTaxMapper;
import managers.SalesTaxFileManager;
import models.database.SalesTaxDBModel;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author ernestholloway
 * <p>
 * This class provides a series of convenience functions to handle seeding the tax data for
 * all of 52 States in the USA to the embedded databse. It also allows the user to search the correct
 * sales tax data by zipcode.
 */
@Singleton
public class SalesTaxSeederFacade {
    private static final String DATABASE_FILENAME = "sales_tax.odb";

    private final SalesTaxFileManager salesTaxFileManager;
    private final SalesTaxMapper salesTaxMapper;
    private final SalesTaxDAO salesTaxDAO;
    private ObjectDBManager objectDBManager;

    @Inject
    public SalesTaxSeederFacade(final SalesTaxFileManager salesTaxFileManager, final SalesTaxMapper salesTaxMapper, final SalesTaxDAO salesTaxDAO) {
        this.salesTaxFileManager = salesTaxFileManager;
        this.salesTaxMapper = salesTaxMapper;
        this.salesTaxDAO = salesTaxDAO;
    }

    public boolean seedSalesTaxData() {
        boolean successful = false;

        //If we've already seeded the data into our database, no need to do it again.
        if (salesTaxDAO.getTotalNumber() > 0) {
            return false;
        }

        String[] fileNames = salesTaxFileManager.getTaxFileNames();

        //The file name should not be null and we should have 52, one tax file for each state.
        if (fileNames == null || fileNames.length < 52) {
            return false;
        }

        //Create the database file
        objectDBManager.openDataBase(DATABASE_FILENAME);

        for (String fileName : fileNames) {
            List<String> rawData = salesTaxFileManager.getSalesTaxData(fileName);

            if (rawData != null && !rawData.isEmpty()) {
                for (String rowContent : rawData) {
                    SalesTaxDBModel dbModel = salesTaxMapper.map(rowContent);

                    if (dbModel != null) {
                        successful = salesTaxDAO.create(dbModel);
                    }

                }
            }
        }

        objectDBManager.closeDataBase(DATABASE_FILENAME);

        return successful;
    }

    public SalesTaxDBModel getSalesTaxDataByZipCode(final String zipCode) {
        return salesTaxDAO.readByZipCode(zipCode);
    }

    public void resetSalesTaxData(){
        salesTaxDAO.deleteAll();
    }
}
