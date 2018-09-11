package managers;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;

/**
 * @author ernestholloway
 * <p>
 * This class is responsible for performing CRUD operations on the actual database object store
 * where all database models will be stored.
 */
@Singleton
public class ObjectDBManager implements IDataBaseManager {
    private EntityManagerFactory entityManagerFactory;

    /**
     * CTOR
     */
    @Inject
    public ObjectDBManager() {
    }

    /**
     * Creates a new object db file, or opens an existing one if available
     *
     * @param fileNameAndPath is the full file name and path for the the .odb file.
     * @return true if successfully created or opened, false otherwise.
     */
    @Override
    public boolean openDataBase(final String fileNameAndPath) {
        try {
            File file = new File(fileNameAndPath);

            if (!doesDataBaseExist(fileNameAndPath)) {
                file.createNewFile();
            }

            entityManagerFactory = Persistence.createEntityManagerFactory(fileNameAndPath);

            return doesDataBaseExist(fileNameAndPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Closes the object db database file
     *
     * @param fileNameAndPath is the full file name and path for the the .odb file
     * @return true if successfully closed, false otherwise.
     */
    @Override
    public boolean closeDataBase(final String fileNameAndPath) {
        if (entityManagerFactory == null) {
            return false;
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.close();

        entityManagerFactory.close();

        boolean isDataBaseClosed = !entityManagerFactory.isOpen();

        entityManagerFactory = null;

        return isDataBaseClosed;
    }

    /**
     * Deletes the object db file entirely from the file system.
     *
     * @param fileNameAndPath is the full file name and path for the the .odb file.
     * @return true if successfully deleted, false otherwise.
     */
    @Override
    public boolean deleteDataBase(final String fileNameAndPath) {
        try {
            File file = new File(fileNameAndPath);
            return file.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Indicates if the .odb file for the object db exists.
     *
     * @param fileNameAndPath is the full file name and path for the the .odb file.
     * @return true if the database file exists, false otherwise.
     */
    @Override
    public boolean doesDataBaseExist(final String fileNameAndPath) {
        try {
            File file = new File(fileNameAndPath);
            return file.exists();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @return the {@link EntityManager} which is required to perform any CRUD operations for any corresponding objects
     * stored in the database.
     */
    public EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            return null;
        } else {
            return entityManagerFactory.createEntityManager();
        }
    }
}
