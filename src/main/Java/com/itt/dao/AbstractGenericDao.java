package com.itt.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class AbstractGenericDao<T> implements GenericDao<T> {

    private SessionFactory sessionFactory;

    public AbstractGenericDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected abstract Class getCurrentClass();

    protected abstract String getUpdateQuery();

    public void create(T object) {
        getCurrentSession().save(object);
    }

    public void update(T object) {
        getCurrentSession().update(object);
    }

    public void delete(Serializable id) {
        getCurrentSession().delete(getCurrentSession().get(getCurrentClass(), id));
    }

    @Transactional(readOnly = true)
    public T read(Serializable id) {
        return (T) getCurrentSession().get(getCurrentClass(), id);
    }

    @Transactional(readOnly = true)
    public List<T> getAll() {
        return (List<T>) getCurrentSession().createCriteria(getCurrentClass()).list();
    }

    @Override
    public void updateStatus(Serializable id, String status) {
        Query query = getCurrentSession().createQuery(getUpdateQuery());
        query.setString("status", status);
        if (id.getClass() == Integer.class) {
            query.setInteger("id", (Integer) id);
        } else if (id.getClass() == String.class) {
            query.setString("id", (String) id);
            query.executeUpdate();
        }
        query.executeUpdate();
    }
}
