package managers;

import java.io.File;

import database.IDataBase;
import org.garret.perst.Database;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

/**
 *
 * @author Erneset Holloway
 *
 *         This class provides a quick and easy way to create, open, and close
 *         PerstDB files, and gain access to JSQL handle to read/write/modify
 *         domain objects to and from the DB.
 *
 */
public class PerstDBManager implements IDataBase {

    private Database database;
    private Storage storage;

    private long pageSize;
    private boolean isMultiThreaded;
    private String encryptionKey;

    public PerstDBManager() {
        storage = StorageFactory.getInstance().createStorage();
        pageSize = 512;
        isMultiThreaded = false;
        database = null;
        encryptionKey = null;
    }

    @Override
    public boolean createDatabase(String fileNameAndPath) {
        try {
            new File(fileNameAndPath).createNewFile();

            storage.open(fileNameAndPath, pageSize);

            database = new Database(storage, isMultiThreaded);

            System.out.println("Database successfully created at:"
                    + fileNameAndPath + " with page size = " + pageSize
                    + " and multi-threaded=" + isMultiThreaded);

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());

            return false;
        }
    }

    @Override
    public boolean createDataBase(String encrpytionKey, String fileNameAndPath) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean openDataBase(String fileNameAndPath) {
        try {
            storage.open(fileNameAndPath, pageSize);
            database = new Database(storage, isMultiThreaded);

            System.out.println("Database successfully opend at:"
                    + fileNameAndPath + " with page size = " + pageSize
                    + " and multi-threaded=" + isMultiThreaded);

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());

            return false;

        }
    }

    @Override
    public boolean closeDataBase(String fileNameAndPath) {
        try {
            storage.close();

            database = null;

            System.out.println("Database successfully closed at:"
                    + fileNameAndPath + " with page size = " + pageSize
                    + " and multi-threaded=" + isMultiThreaded);

            // Reset back to default values
            pageSize = 512;
            isMultiThreaded = false;

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());

            return false;

        }
    }

    @Override
    public boolean deleteDataBase(String fileNameAndPath) {
        try {
            new File(fileNameAndPath).delete();
            System.out.println("Successfully removed datbase file:"
                    + fileNameAndPath);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doesDataBaseExist(String fileNameAndPath) {
        try {
            boolean doesFileExist = new File(fileNameAndPath).exists();
            System.out.println("Datbase file:" + fileNameAndPath + " exists:"
                    + doesFileExist);
            return doesFileExist;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @return
     */
    public long getPageSize() {
        return pageSize;
    }

    /**
     *
     * @param pageSize
     */
    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    /**
     *
     * @return
     */
    public Database getDataBase() {
        return database;
    }

    /**
     *
     * @return
     */
    public boolean isMultiThreaded() {
        return isMultiThreaded;
    }

    /**
     *
     * @param isMultiThreaded
     */
    public void setMultiThreaded(boolean isMultiThreaded) {
        this.isMultiThreaded = isMultiThreaded;
    }

    /**
     *
     * @return
     */
    public String getEncryptionKey() {
        return encryptionKey;
    }

    /**
     *
     * @param encryptionKey
     */
    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }
}
