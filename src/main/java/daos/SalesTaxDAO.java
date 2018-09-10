package daos;

import managers.ObjectDBManager;
import models.database.SalesTaxDBModel;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class SalesTaxDAO implements ISalesTaxDAO {
    private final ObjectDBManager objectDBManager;

    @Inject
    public SalesTaxDAO(final ObjectDBManager objectDBManager) {
        this.objectDBManager = objectDBManager;
    }

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

    @Override
    public SalesTaxDBModel readByZipCode(final String zipCode) {
        SalesTaxDBModel model = null;

        try {
            EntityManager entityManager = objectDBManager.getEntityManager();
            TypedQuery<SalesTaxDBModel> query = entityManager.createQuery("SELECT t FROM Point t WHERE zipCode = " + zipCode, SalesTaxDBModel.class);
            model = query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return model;
    }

    @Override
    public int getTotalNumber() {
        int totalNumber = 0;

        try {
            EntityManager entityManager = objectDBManager.getEntityManager();
            Query query = entityManager.createQuery("SELECT COUNT(t) FROM SalesTaxDBModel t");
            totalNumber = (int) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Total Sales Tax Records: " + totalNumber);

        return totalNumber;
    }

    @Override
    public boolean deleteAll() {
        EntityManager entityManager = objectDBManager.getEntityManager();

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
