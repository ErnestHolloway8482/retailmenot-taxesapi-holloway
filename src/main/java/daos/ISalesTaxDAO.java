package daos;

import models.database.SalesTaxDBModel;

public interface ISalesTaxDAO {
    public boolean create(final SalesTaxDBModel model);
    public SalesTaxDBModel readByZipCode(final String zipCode);
    public int getTotalNumber();
    public boolean deleteAll();
}
