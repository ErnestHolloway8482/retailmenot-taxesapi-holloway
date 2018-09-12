package daos;

import models.database.SalesTaxDBModel;

/**
 * @author ernestholloway
 * <p>
 * This is the interface for performing CRUD operations for the {@link SalesTaxDBModel}
 */
public interface ISalesTaxDAO {
    public boolean create(final SalesTaxDBModel model);

    public SalesTaxDBModel readByZipCode(final String zipCode);

    public long getTotalNumber();

    public boolean deleteAll();
}
