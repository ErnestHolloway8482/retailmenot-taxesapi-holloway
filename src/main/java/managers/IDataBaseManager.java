package managers;

/**
 * @author ernestholloway
 *
 * This is the interface used for any class that should manage global database operations.
 */
public interface IDataBaseManager {
    public static final int DATA_BASE_VERSION_NUMBER = 1;

    public boolean openDataBase(String fileNameAndPath);

    public boolean closeDataBase(String fileNameAndPath);

    public boolean deleteDataBase(String fileNameAndPath);

    public boolean doesDataBaseExist(String fileNameAndPath);
}
