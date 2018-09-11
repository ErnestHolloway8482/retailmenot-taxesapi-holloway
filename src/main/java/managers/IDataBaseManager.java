package managers;

public interface IDataBaseManager {
    public static final int DATA_BASE_VERSION_NUMBER = 1;

    public boolean openDataBase(String fileNameAndPath);

    public boolean closeDataBase(String fileNameAndPath);

    public boolean deleteDataBase(String fileNameAndPath);

    public boolean doesDataBaseExist(String fileNameAndPath);
}
