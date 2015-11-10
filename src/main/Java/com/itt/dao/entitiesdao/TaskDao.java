package com.itt.dao.entitiesdao;

import com.itt.dao.AbstractGenericDao;
import com.itt.entity.Task;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TaskDao extends AbstractGenericDao<Task> implements ITaskDao{

    public TaskDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class getCurrentClass() {
        return Task.class;
    }

    @Override
    protected String getUpdateQuery() {
        return "update Task set status=:status where id=:id";
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> getTasksWithMaxPriorityForExecutor() {
        return getCurrentSession().createQuery("select t from Task t join fetch t.executor te " +
                                                "where te.status='active' and t.priority in " +
                                                "(select max(task.priority) from Task task " +
                                                "where task.executor.id=te.id and task.status='not start')").list();
    }
}
