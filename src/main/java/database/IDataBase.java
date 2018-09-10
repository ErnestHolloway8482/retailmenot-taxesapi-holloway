package database;

public interface IDataBase {
    public static final int DATA_BASE_VERSION_NUMBER = 1;

    public boolean createDatabase(String fileNameAndPath);

    public boolean openDataBase(String fileNameAndPath);

    public boolean closeDataBase(String fileNameAndPath);

    public boolean deleteDataBase(String fileNameAndPath);

    public boolean doesDataBaseExist(String fileNameAndPath);
}
