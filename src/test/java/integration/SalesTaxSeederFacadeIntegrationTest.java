package integration;

import daos.SalesTaxDAO;
import facades.SalesTaxSeederFacade;
import managers.ObjectDBManager;
import managers.SalesTaxFileManager;
import mappers.SalesTaxMapper;
import models.database.SalesTaxDBModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author ernestholloway
 * <p>
 * Integration Test Class that tests combination of {@link SalesTaxFileManager},
 * {@link SalesTaxMapper}, {@link ObjectDBManager}, and {@link SalesTaxDAO}
 */
@RunWith(JUnit4.class)
public class SalesTaxSeederFacadeIntegrationTest {
    private SalesTaxSeederFacade salesTaxSeederFacade;

    @Before
    public void setup() {
        SalesTaxFileManager salesTaxFileManager = new SalesTaxFileManager();
        SalesTaxMapper salesTaxMapper = new SalesTaxMapper();
        ObjectDBManager dbManager = new ObjectDBManager();
        SalesTaxDAO salesTaxDAO = new SalesTaxDAO(dbManager);

        if (salesTaxSeederFacade == null) {
            salesTaxSeederFacade = new SalesTaxSeederFacade(salesTaxFileManager, salesTaxMapper, salesTaxDAO, dbManager);
        }

        salesTaxSeederFacade.setDatabaseFileName("sales_tax_test.odb");
        salesTaxSeederFacade.deleteSalesTaxDatabaseFile();
    }

    @After
    public void tearDown() {
        salesTaxSeederFacade.deleteSalesTaxDatabaseFile();
    }

    @Test
    public void testSeedTaxData() {
        Assert.assertTrue(salesTaxSeederFacade.seedSalesTaxData());

        //If we already seeded the data we shouldn't be allowed to do it again.
        Assert.assertFalse(salesTaxSeederFacade.seedSalesTaxData());
    }

    @Test
    public void testGetSalesTaxDataByZipCode() {
        Assert.assertTrue(salesTaxSeederFacade.seedSalesTaxData());

        SalesTaxDBModel salesTaxDBModelAustinTX = salesTaxSeederFacade.getSalesTaxDataByZipCode("78753");
        SalesTaxDBModel salesTaxDBModelNewLondonCT = salesTaxSeederFacade.getSalesTaxDataByZipCode("06320");

        //State,ZipCode,TaxRegionName,StateRate,EstimatedCombinedRate,EstimatedCountyRate,EstimatedCityRate,EstimatedSpecialRate,RiskLevel

        //TX,78753,AUSTIN,0.062500,0.082500,0,0.010000,0.010000,4
        Assert.assertNotNull(salesTaxDBModelAustinTX);
        Assert.assertNotNull(salesTaxDBModelAustinTX.getUuid());
        Assert.assertTrue(salesTaxDBModelAustinTX.getState().equals("TX"));
        Assert.assertTrue(salesTaxDBModelAustinTX.getZipCode().equals("78753"));
        Assert.assertTrue(salesTaxDBModelAustinTX.getTaxRegionName().equals("AUSTIN"));
        Assert.assertEquals(0.062500, salesTaxDBModelAustinTX.getStateRate(), .001);
        Assert.assertEquals(0.082500, salesTaxDBModelAustinTX.getEstimatedCombinedRate(), .001);
        Assert.assertEquals(0, salesTaxDBModelAustinTX.getEstimatedCountyRate(), .001);
        Assert.assertEquals(0.010000, salesTaxDBModelAustinTX.getEstimatedCityRate(), .001);
        Assert.assertEquals(0.010000, salesTaxDBModelAustinTX.getEstimatedSpecialRate(), .001);
        Assert.assertTrue(salesTaxDBModelAustinTX.getRiskLevel() == 4);

        //CT,06320,"CONNECTICUT STATE",0.063500,0.063500,0,0,0,0
        Assert.assertNotNull(salesTaxDBModelNewLondonCT);
        Assert.assertNotNull(salesTaxDBModelNewLondonCT.getUuid());
        Assert.assertTrue(salesTaxDBModelNewLondonCT.getState().equals("CT"));
        Assert.assertTrue(salesTaxDBModelNewLondonCT.getZipCode().equals("06320"));
        Assert.assertEquals("\"CONNECTICUT STATE\"", salesTaxDBModelNewLondonCT.getTaxRegionName());
        Assert.assertEquals(0.063500, salesTaxDBModelNewLondonCT.getStateRate(), .001);
        Assert.assertEquals(0.063500, salesTaxDBModelNewLondonCT.getEstimatedCombinedRate(), .001);
        Assert.assertEquals(0, salesTaxDBModelNewLondonCT.getEstimatedCountyRate(), .001);
        Assert.assertEquals(0, salesTaxDBModelNewLondonCT.getEstimatedCityRate(), .001);
        Assert.assertEquals(0, salesTaxDBModelNewLondonCT.getEstimatedSpecialRate(), .001);
        Assert.assertTrue(salesTaxDBModelNewLondonCT.getRiskLevel() == 0);

        //We shouldn't find a SalesTaxDBModel for an unknown zipcode.
        Assert.assertNull(salesTaxSeederFacade.getSalesTaxDataByZipCode("123456"));
    }

    @Test
    public void testDeleteSalesTaxDatabaseFile() {
        Assert.assertTrue(salesTaxSeederFacade.seedSalesTaxData());
        Assert.assertTrue(salesTaxSeederFacade.deleteSalesTaxDatabaseFile());
    }
}
