package managers;

import database.IDataBase;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
public class ObjectDBManager {
    private EntityManagerFactory entityManagerFactory;


    @Inject
    public ObjectDBManager() {
    }

    public boolean openDataBase(final String fileNameAndPath) {
        entityManagerFactory = Persistence.createEntityManagerFactory(fileNameAndPath);

        return entityManagerFactory != null;
    }

    public boolean closeDataBase(final String fileNameAndPath) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.close();

        entityManagerFactory.close();

        return !entityManagerFactory.isOpen();
    }

    public boolean deleteDataBase(String fileNameAndPath) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.close();

        entityManagerFactory.close();

        return false;
    }

    public boolean doesDataBaseExist(String fileNameAndPath) {
        entityManagerFactory.createEntityManager();
        return false;
    }

    public EntityManager getEntityManager(){
        if(entityManagerFactory==null){
            return null;
        } else{
            return entityManagerFactory.createEntityManager();
        }
    }
}
