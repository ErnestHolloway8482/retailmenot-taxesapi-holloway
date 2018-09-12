package unit;

import mappers.SalesTaxMapper;
import models.database.SalesTaxDBModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author ernestholloway
 * <p>
 * Unit Test Class that tests the {@link SalesTaxMapper}
 */
@RunWith(JUnit4.class)
public class SalesTaxMapperTest {
    private SalesTaxMapper salesTaxMapper;

    @Before
    public void setup() {
        salesTaxMapper = new SalesTaxMapper();
    }

    @Test
    public void testMapper() {
        String value = "";
        SalesTaxDBModel model;

        //Invalid Value empty string
        Assert.assertNull(salesTaxMapper.map(value));

        //Invalid Value too many parameters
        value = "AK,99556,KENAI PENINSULA BURROUGH,0.000000,0.030000,0.030000,0,0,2,5,6";
        Assert.assertNull(salesTaxMapper.map(value));

        //Invalid Value null string.
        value = null;
        Assert.assertNull(salesTaxMapper.map(value));

        //Invalid Value too few parameters
        value = "AK,99556,KENAI PENINSULA BURROUGH,0.000000";
        Assert.assertNull(salesTaxMapper.map(value));

        //Regular normal sales tax entry
        //"State,ZipCode,TaxRegionName,StateRate,EstimatedCombinedRate,EstimatedCountyRate,EstimatedCityRate,EstimatedSpecialRate,RiskLevel";
        value = "AK,99556,KENAI PENINSULA BURROUGH,0.000000,0.030000,0.030000,0,0,2";

        model = salesTaxMapper.map(value);
        Assert.assertEquals("AK", model.getState());
        Assert.assertEquals("99556", model.getZipCode());
        Assert.assertEquals("KENAI PENINSULA BURROUGH", model.getTaxRegionName());
        Assert.assertEquals(0, model.getStateRate(), .01);
        Assert.assertEquals(0.030000, model.getEstimatedCombinedRate(), .01);
        Assert.assertEquals(0.030000, model.getEstimatedCountyRate(), .01);
        Assert.assertEquals(0, model.getEstimatedCityRate(), .01);
        Assert.assertEquals(0, model.getEstimatedSpecialRate(), .01);
        Assert.assertEquals(2, model.getRiskLevel());
    }

    @Test
    public void testParseFloat() {
        String value = "";

        //Not a number
        Assert.assertEquals(-1, salesTaxMapper.parseFloat(value), .01);

        //Integer
        value = "1";
        Assert.assertEquals(1.0, salesTaxMapper.parseFloat(value), .01);

        //Float
        value = "1.41";
        Assert.assertEquals(1.41, salesTaxMapper.parseFloat(value), .01);
    }

    @Test
    public void testParseInteger() {
        String value = "";

        //Not a number
        Assert.assertEquals(-1, salesTaxMapper.parseInteger(value));

        //Integer
        value = "1";
        Assert.assertEquals(1, salesTaxMapper.parseInteger(value));

        //Float
        value = "1.41";
        Assert.assertEquals(-1, salesTaxMapper.parseInteger(value));
    }
}
