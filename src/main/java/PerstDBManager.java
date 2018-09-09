import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

public class PerstDBManager {
    private final Storage storage;

    public PerstDBManager(){
        storage = StorageFactory.getInstance().createStorage();
    }

    public void createStorageDatabse(final String databaseName){
        storage.open(databaseName, 1024);
    }
}
