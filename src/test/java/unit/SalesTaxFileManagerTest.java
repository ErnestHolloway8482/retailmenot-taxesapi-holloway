package unit;

import managers.SalesTaxFileManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

/**
 * @author ernestholloway
 *
 * Unit Test Class that tests the {@link SalesTaxFileManager}
 */
@RunWith(JUnit4.class)
public class SalesTaxFileManagerTest {
    private static final String CSV_ROW_HEADER = "State,ZipCode,TaxRegionName,StateRate,EstimatedCombinedRate,EstimatedCountyRate,EstimatedCityRate,EstimatedSpecialRate,RiskLevel";
    private SalesTaxFileManager salesTaxFileManager;

    @Before
    public void setup() {
        salesTaxFileManager = new SalesTaxFileManager();
    }

    @Test
    public void testFileNamesPresent() {
        String[] fileNames = salesTaxFileManager.getTaxFileNames();

        Assert.assertNotNull(fileNames);
        Assert.assertTrue(fileNames.length > 0);

        //There should be 52 files to match the 52 states.
        Assert.assertTrue(fileNames.length == 52);
    }

    @Test
    public void testFileNamesCanBeRead() {
        String[] fileNames = salesTaxFileManager.getTaxFileNames();

        for (String fileName : fileNames) {
            List<String> rawData = salesTaxFileManager.getSalesTaxData(fileName);

            Assert.assertNotNull(rawData);

            for (String rowContent : rawData) {
                Assert.assertNotNull(rowContent);
                Assert.assertFalse(rowContent.contains(CSV_ROW_HEADER));
            }
        }
    }

}
