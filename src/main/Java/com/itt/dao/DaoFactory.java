package com.itt.dao;

import com.itt.dao.entitiesdao.ExecutorDao;
import com.itt.dao.entitiesdao.TaskDao;
import org.hibernate.SessionFactory;

public final class DaoFactory {

    private static DaoFactory factory;

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        if (factory == null) {
            factory = new DaoFactory();
        }
        return factory;
    }

    public ExecutorDao getExecutorDao(SessionFactory sessionFactory) {
        return new ExecutorDao(sessionFactory);
    }

    public TaskDao getTaskDao(SessionFactory sessionFactory) {
        return new TaskDao(sessionFactory);
    }
}
