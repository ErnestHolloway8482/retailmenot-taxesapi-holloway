package controllers;

import com.google.gson.Gson;
import daos.SalesTaxDAO;
import facades.SalesTaxSeederFacade;
import managers.ObjectDBManager;
import managers.SalesTaxFileManager;
import mappers.SalesTaxMapper;
import models.database.SalesTaxDBModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ernestholloway
 * <p>
 * This is a Spring based {@link RestController} class that allows the user to get the appropriate
 * sales tax data that was seeded.
 */
@RestController
public class SalesTaxRestController {
    private static final String NO_SALES_TAX_ENTRY_FOUND = "{\n\"No Sales Tax Entry Found For Zip Code\"\n}";
    private static final String SALES_TAX_DELETED_PASS = "{\n\"True\"\n}";
    private static final String SALES_TAX_DELETED_FAIL = "{\n\"False\"\n}";

    private final SalesTaxSeederFacade salesTaxSeederFacade;

    /**
     * CTOR
     */
    public SalesTaxRestController() {
        ObjectDBManager objectDBManager = new ObjectDBManager();
        SalesTaxFileManager salesTaxFileManager = new SalesTaxFileManager();
        SalesTaxMapper salesTaxMapper = new SalesTaxMapper();
        SalesTaxDAO salesTaxDAO = new SalesTaxDAO(objectDBManager);

        salesTaxSeederFacade = new SalesTaxSeederFacade(salesTaxFileManager, salesTaxMapper, salesTaxDAO, objectDBManager);
    }

    /**
     * Allows the user to search for the sales tax information by ZipCode. It exposes a GET Restful API call
     * for the user to retrieve the information.
     *
     * @param zipCode is the zipcode containing the sales tax data.
     * @return if a valid entry is found it will return the JSON representation of the {@link SalesTaxDBModel}, if no valid entry
     * is found the JSON returned will NO_SALES_TAX_ENTRY_FOUND string defined in the constants above.
     */
    @RequestMapping(value = "/salesTax", method = {RequestMethod.GET})
    public String getSalesTaxByZipCode(@RequestParam(required = true) final String zipCode) {
        //Attempt to seed the data first before returning the response.
        salesTaxSeederFacade.seedSalesTaxData();

        SalesTaxDBModel model = salesTaxSeederFacade.getSalesTaxDataByZipCode(zipCode);
        String jsonResponse;

        if (model == null) {
            jsonResponse = NO_SALES_TAX_ENTRY_FOUND;
        } else {
            Gson gson = new Gson();
            jsonResponse = gson.toJson(model, SalesTaxDBModel.class);
        }

        System.out.println("Json Response:" + jsonResponse);

        return jsonResponse;
    }

    /**
     * This will kill the online database for the seeded sales tax entries. This is a destructive command and not recommended to be used
     * unless it is necessary to allow the database to be restored.
     *
     * @return JSON string representation of the success of the database. The success response is SALES_TAX_DELETED_PASS defined above,
     * the failed response is SALES_TAX_DELETED_FAIL.
     */
    @RequestMapping(value = "/salesTax", method = {RequestMethod.DELETE})
    public String deleteSalesTaxEntries() {
        String jsonResponse;

        //Attempt to seed the data first before returning the response.
        boolean successful = salesTaxSeederFacade.deleteSalesTaxDatabaseFile();

        if (successful) {
            jsonResponse = SALES_TAX_DELETED_PASS;
        } else {
            jsonResponse = SALES_TAX_DELETED_FAIL;
        }

        System.out.println("Json Response:" + jsonResponse);

        return jsonResponse;
    }
}
