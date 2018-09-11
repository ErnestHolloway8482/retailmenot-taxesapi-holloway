import facades.SalesTaxSeederFacade;
import managers.ObjectDBManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ObjectDBManagerTest {
    final private static String TEST_DATABASE_FILENAME = "sales_tax_test.odb";
    final private static String TEST_DATABASE_FILENAME_BACKUP = "sales_tax_test.odb$";

    final ObjectDBManager objectDBManager = new ObjectDBManager();

    @Before
    public void setup() {
        objectDBManager.deleteDataBase(TEST_DATABASE_FILENAME);
        objectDBManager.deleteDataBase(TEST_DATABASE_FILENAME_BACKUP);
        Assert.assertTrue(objectDBManager.openDataBase(TEST_DATABASE_FILENAME));
    }

    @After
    public void tearDown() {
        objectDBManager.deleteDataBase(TEST_DATABASE_FILENAME);
        objectDBManager.deleteDataBase(TEST_DATABASE_FILENAME_BACKUP);
    }

    @Test
    public void testDoesDataBaseFileExist() {
        Assert.assertTrue(objectDBManager.doesDataBaseExist(TEST_DATABASE_FILENAME));
    }

    @Test
    public void testOpenDataBase() {
        Assert.assertTrue(objectDBManager.openDataBase(TEST_DATABASE_FILENAME));
    }

    @Test
    public void testCloseDataBase() {
        Assert.assertTrue(objectDBManager.closeDataBase(TEST_DATABASE_FILENAME));
    }

    @Test
    public void testGetEntityManager() {
        Assert.assertNotNull(objectDBManager.getEntityManager());
    }
}
