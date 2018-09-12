package controllers;

import com.google.gson.Gson;
import facades.SalesTaxSeederFacade;
import models.database.SalesTaxDBModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Singleton;

@RestController
@Singleton
public class SalesTaxRestController {
    private static final String NORMAL_RESPONSE_FORMAT = "{\n\t%s\n}";
    private static final String NO_SALES_TAX_ENTRY_FOUND = "{\n\"No Sales Tax Entry Found For Zip Code\"\n}";
    private static final String SALES_TAX_DELETED_PASS = "{\n\"True\"\n}";
    private static final String SALES_TAX_DELETED_FAIL = "{\n\"False\"\n}";

    private final SalesTaxSeederFacade salesTaxSeederFacade;

    public SalesTaxRestController(final SalesTaxSeederFacade salesTaxSeederFacade) {
        this.salesTaxSeederFacade = salesTaxSeederFacade;
    }

    @RequestMapping("/getSalesTax")
    public String getSalesTaxByZipCode(@RequestParam(required = true) final String zipCode) {
        //Attempt to seed the data first before returning the response.
        salesTaxSeederFacade.seedSalesTaxData();

        SalesTaxDBModel model = salesTaxSeederFacade.getSalesTaxDataByZipCode(zipCode);
        String jsonResponse;

        if (model == null) {
            jsonResponse = NO_SALES_TAX_ENTRY_FOUND;
        } else {
            Gson gson = new Gson();
            jsonResponse = String.format(NORMAL_RESPONSE_FORMAT, gson.toJson(model, SalesTaxDBModel.class));
        }

        System.out.println("Json Response:" + jsonResponse);

        return jsonResponse;
    }

    @RequestMapping("/deleteSalesTaxEntries")
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
