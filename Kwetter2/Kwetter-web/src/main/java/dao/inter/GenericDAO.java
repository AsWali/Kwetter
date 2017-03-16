package dao.inter;

import javax.ejb.Stateless;
import java.util.Map;

/**
 * Created by Asror on 11-3-2017.
 */

public interface GenericDAO<T> {
    /**
     * Method that returns the number of entries from a table that meet some
     * criteria (where clause params)
     *
     * @param params
     *            sql parameters
     * @return the number of records meeting the criteria
     */
    long countAll(Map<String, Object> params);

    T create(T t);

    void delete(Object id);

    T find(Object id);

    T update(T t);
}