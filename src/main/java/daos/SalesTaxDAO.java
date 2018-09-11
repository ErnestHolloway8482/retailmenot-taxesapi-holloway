package daos;

import managers.ObjectDBManager;
import models.database.SalesTaxDBModel;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author ernestholloway
 * <p>
 * This DAO class is used to perform CRUD operations for the {@link SalesTaxDBModel}.
 */
public class SalesTaxDAO implements ISalesTaxDAO {
    private final ObjectDBManager objectDBManager;

    /**
     * CTOR
     *
     * @param objectDBManager is the database manager class to allow for the DAO CRUD based operations.
     */
    @Inject
    public SalesTaxDAO(final ObjectDBManager objectDBManager) {
        this.objectDBManager = objectDBManager;
    }

    /**
     * Stores a {@link SalesTaxDBModel} into the object database.
     *
     * @param model is a {@link SalesTaxDBModel}
     * @return true if successfully stored, false otherwise.
     */
    @Override
    public boolean create(final SalesTaxDBModel model) {
        EntityManager entityManager = objectDBManager.getEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(model);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the {@link SalesTaxDBModel} by the entered zipcode. The assumpion is that each sales tax item is unique to a specific
     * zicode.
     *
     * @param zipCode is the USA based zipcode.
     * @return {@link SalesTaxDBModel} if there is a matching zipcode, null otherwise.
     */
    @Override
    public SalesTaxDBModel readByZipCode(final String zipCode) {
        SalesTaxDBModel model = null;

        String queryString = "SELECT t FROM SalesTaxDBModel t WHERE zipCode == \"%s\"";

        try {
            EntityManager entityManager = objectDBManager.getEntityManager();
            TypedQuery<SalesTaxDBModel> query = entityManager.createQuery(String.format(queryString, zipCode), SalesTaxDBModel.class);
            model = query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return model;
    }

    /**
     * @return the total number of {@link SalesTaxDBModel} in the database.
     */
    @Override
    public long getTotalNumber() {
        long totalNumber = 0;

        try {
            EntityManager entityManager = objectDBManager.getEntityManager();
            Query query = entityManager.createQuery("SELECT COUNT(t) FROM SalesTaxDBModel t");
            totalNumber = (Long) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Total Sales Tax Records: " + totalNumber);

        return totalNumber;
    }

    /**
     * Deleates all of the {@link SalesTaxDBModel} inside of the object database.
     *
     * @return true if all items are successfully deleted, false otherwise.
     */
    @Override
    public boolean deleteAll() {
        EntityManager entityManager = objectDBManager.getEntityManager();

        if (entityManager == null) {
            return false;
        }

        try {
            TypedQuery<SalesTaxDBModel> query = entityManager.createQuery("SELECT t FROM SalesTaxDBModel t", SalesTaxDBModel.class);
            List<SalesTaxDBModel> results = query.getResultList();

            entityManager.getTransaction().begin();

            for (SalesTaxDBModel t : results) {
                entityManager.remove(t);
            }

            entityManager.getTransaction().commit();

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());

            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            return false;
        }
    }
}
