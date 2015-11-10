package com.itt.dao.entitiesdao;

import com.itt.dao.AbstractGenericDao;
import com.itt.entity.Executor;
import org.hibernate.SessionFactory;

public class ExecutorDao extends AbstractGenericDao<Executor> {

    public ExecutorDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class getCurrentClass() {
        return Executor.class;
    }

    @Override
    protected String getUpdateQuery() {
        return "update Executor set status=:status where name=:id";
    }

}
