import daos.ISalesTaxDAO;
import daos.SalesTaxDAO;
import managers.ObjectDBManager;
import models.database.SalesTaxDBModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SalesTaxDAOTest {
    final private static String TEST_DATABASE_FILENAME = "sales_tax_test.odb";
    final private static String TEST_DATABASE_FILENAME_BACKUP = "sales_tax_test.odb$";

    ISalesTaxDAO salesTaxDAO;
    ObjectDBManager dbManager;

    @Before
    public void setup() {
        dbManager = new ObjectDBManager();
        dbManager.openDataBase(TEST_DATABASE_FILENAME);
        salesTaxDAO = new SalesTaxDAO(dbManager);
    }

    @After
    public void tearDown() {
        Assert.assertTrue(salesTaxDAO.deleteAll());
        dbManager.deleteDataBase(TEST_DATABASE_FILENAME);
        dbManager.deleteDataBase(TEST_DATABASE_FILENAME_BACKUP);
    }

    @Test
    public void testCreateSalesTaxDBModel() {
        SalesTaxDBModel model = new SalesTaxDBModel();

        Assert.assertTrue(salesTaxDAO.create(model));
    }

    @Test
    public void testReadByZipCode() {
        SalesTaxDBModel model = new SalesTaxDBModel();
        model.setZipCode("78753");

        Assert.assertTrue(salesTaxDAO.create(model));

        SalesTaxDBModel readModel = salesTaxDAO.readByZipCode("78753");

        Assert.assertNotNull(readModel);

        Assert.assertEquals(model.getZipCode(), readModel.getZipCode());
        Assert.assertEquals(model.getUuid(), readModel.getUuid());
    }

    @Test
    public void testGetTotalNumber() {
        Assert.assertEquals(0, salesTaxDAO.getTotalNumber());

        SalesTaxDBModel model = new SalesTaxDBModel();
        Assert.assertTrue(salesTaxDAO.create(model));
        Assert.assertEquals(1, salesTaxDAO.getTotalNumber());

        model = new SalesTaxDBModel();
        Assert.assertTrue(salesTaxDAO.create(model));
        Assert.assertEquals(2, salesTaxDAO.getTotalNumber());

        model = new SalesTaxDBModel();
        Assert.assertTrue(salesTaxDAO.create(model));
        Assert.assertEquals(3, salesTaxDAO.getTotalNumber());
    }

    @Test
    public void testDeleteAll(){
        Assert.assertEquals(0, salesTaxDAO.getTotalNumber());

        //Create 100 entries
        for(int i=0; i<100; i++){
            SalesTaxDBModel model = new SalesTaxDBModel();
            Assert.assertTrue(salesTaxDAO.create(model));
        }

        Assert.assertEquals(100, salesTaxDAO.getTotalNumber());

        Assert.assertTrue(salesTaxDAO.deleteAll());

        Assert.assertEquals(0, salesTaxDAO.getTotalNumber());
    }
}
