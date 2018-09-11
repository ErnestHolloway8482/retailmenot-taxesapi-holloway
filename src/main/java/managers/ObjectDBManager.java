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
public class ObjectDBManager {
    private EntityManagerFactory entityManagerFactory;

    @Inject
    public ObjectDBManager() {
    }

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

    public boolean closeDataBase(final String fileNameAndPath) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.close();

        entityManagerFactory.close();

        boolean isDataBaseClosed = !entityManagerFactory.isOpen();

        entityManagerFactory = null;

        return isDataBaseClosed;
    }

    public boolean deleteDataBase(final String fileNameAndPath) {
        try {
            File file = new File(fileNameAndPath);
            return file.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean doesDataBaseExist(final String fileNameAndPath) {
        try {
            File file = new File(fileNameAndPath);
            return file.exists();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            return null;
        } else {
            return entityManagerFactory.createEntityManager();
        }
    }
}
