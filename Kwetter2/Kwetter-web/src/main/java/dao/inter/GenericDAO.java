package dao.inter;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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


    /**
     * Method that return the EntityManager that the AbstractDAO uses
     * @return the EntityManager
     */
    EntityManager getEm();

    /**
     * Set the EntityManager of the DAO. Used for testing.
     * @param em
     *          entity manager
     */
    void setEm(EntityManager em);

    /**
     * Method that creates a new Entity. It used for all Entity Types.
     * @param t
     *          Abstract Entity Class
     * @return the Created Entity
     */
    T create(T t);

    /**
     * Method that deletes a Entity.
     * @param id
     *          Entity Id
     */
    void delete(Object id);

    /**
     * Method that finds an Entity.
     * @param id
     *          Entity Id
     * @return the Entity that has the belongs to the parameter
     */
    T find(Object id);

    /**
     * Method that updates an Entity. Updates if the object exist.
     * Creates it if it doesn't exist. So an extra check is needed.
     * @param t
     *          Entity
     * @return the updated Entity
     */
    T update(T t);
}