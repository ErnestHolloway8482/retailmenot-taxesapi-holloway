package facades;

import daos.SalesTaxDAO;
import managers.ObjectDBManager;
import managers.SalesTaxFileManager;
import mappers.SalesTaxMapper;
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
    public static final int TOTAL_NUMBER_OF_TAX_FILES = 52;

    private final SalesTaxFileManager salesTaxFileManager;
    private final SalesTaxMapper salesTaxMapper;
    private final SalesTaxDAO salesTaxDAO;
    private final ObjectDBManager objectDBManager;

    private String databaseFileName = "sales_tax.odb";
    private String databaseFileNameBackup = "sales_tax.odb$";

    /**
     * CTOR
     *
     * @param salesTaxFileManager
     * @param salesTaxMapper
     * @param salesTaxDAO
     * @param objectDBManager
     */
    @Inject
    public SalesTaxSeederFacade(final SalesTaxFileManager salesTaxFileManager, final SalesTaxMapper salesTaxMapper, final SalesTaxDAO salesTaxDAO, final ObjectDBManager objectDBManager) {
        this.salesTaxFileManager = salesTaxFileManager;
        this.salesTaxMapper = salesTaxMapper;
        this.salesTaxDAO = salesTaxDAO;
        this.objectDBManager = objectDBManager;
    }

    /**
     * Sets the file name and path for the database file and auto-sets the backup file that ends in "$".
     *
     * @param fileNameAndPath is the full file name and path for the database file.
     */
    public void setDatabaseFileName(final String fileNameAndPath) {
        if (fileNameAndPath == null) {
            return;
        }

        databaseFileName = fileNameAndPath;
        databaseFileNameBackup = fileNameAndPath + "$";
    }

    /**
     * Seeds all of the .csv files containing the sales tax information into the object database.
     *
     * @return true if successfully seeded, false otherwise.
     */
    public boolean seedSalesTaxData() {
        boolean successful = false;

        //If we've already seeded the data into our database, no need to do it again.
        if (salesTaxDAO.getTotalNumber() > 0) {
            System.out.println("Sales Tax Data Has Already Been Seeded.");
            return false;
        }

        String[] fileNames = salesTaxFileManager.getTaxFileNames();

        //The file name should not be null and we should have 52, one tax file for each state.
        if (fileNames == null || fileNames.length < TOTAL_NUMBER_OF_TAX_FILES) {
            System.out.println("The Sales Tax Data Cannot Be Accessed.");
            return false;
        }

        //Create the database file. If we can't create it, bail.
        if (!objectDBManager.openDataBase(databaseFileName)) {
            System.out.println("The database cannot be opened.");
            return false;
        }

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

        return successful;
    }

    /**
     * Returns a {@link SalesTaxDBModel} based on the zipCode.
     *
     * @param zipCode is the zipCode that is used to search for the corresponding sales tax.
     * @return {@link SalesTaxDBModel} if found for the zipcode, null otherwise.
     */
    public SalesTaxDBModel getSalesTaxDataByZipCode(final String zipCode) {
        return salesTaxDAO.readByZipCode(zipCode);
    }

    /**
     * Removes all of the {@link SalesTaxDBModel} from the database and deletes the database file.
     *
     * @return true if the sales tax info and database file are successfully deleted, false otherwise.
     */
    public boolean deleteSalesTaxDatabaseFile() {
        boolean dataCleared = salesTaxDAO.deleteAll();

        //If we can't close the database bail.
        objectDBManager.closeDataBase(databaseFileName);

        boolean databaseFileCleared = objectDBManager.deleteDataBase(databaseFileName);
        objectDBManager.deleteDataBase(databaseFileNameBackup);

        return dataCleared && databaseFileCleared;
    }
}
