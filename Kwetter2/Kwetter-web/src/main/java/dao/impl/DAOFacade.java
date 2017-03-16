package dao.impl;

import dao.inter.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Map;

/**
 * Created by Asror on 13-3-2017.
 */
public abstract class DAOFacade<T> implements GenericDAO<T> {

    private final Class<T> type;

    public DAOFacade(Class<T> entityClass){
        this.type = entityClass;
    }

    @PersistenceContext
    protected EntityManager em;

    @Override
    public T create(final T t) {
        this.em.persist(t);
        return t;
    }

    @Override
    public long countAll(final Map<String, Object> params) {

        final StringBuffer queryString = new StringBuffer(
                "SELECT count(o) from ");

        queryString.append(type.getSimpleName()).append(" o ");
        // queryString.append(this.getQueryClauses(params, null));

        final Query query = this.em.createQuery(queryString.toString());

        return (Long) query.getSingleResult();

    }

    @Override
    public void delete(final Object id) {
        this.em.remove(this.em.getReference(type, id));
    }

    @Override
    public T find(final Object id) {
        return (T) this.em.find(type, id);
    }

    @Override
    public T update(final T t) {
        return this.em.merge(t);
    }
}
