package com.itt.service;

import com.itt.dao.GenericDao;
import com.itt.entity.Executor;
import com.itt.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.util.List;

public class ExecutorService {

    @Autowired
    @Qualifier("executorDao")
    private GenericDao executorDao;

    public Executor getExecutorById(Serializable id) {
        return (Executor) executorDao.read(id);
    }

    public void deleteExecutorById(Serializable id) {
        executorDao.delete(id);
    }

    public void createExecutor(Executor executor) {
        executorDao.create(executor);
    }

    public void editExecutor(Executor executor) {
        executorDao.update(executor);
    }

    public List<Executor> getAllExecutors() {
        return executorDao.getAll();
    }

    public List<Task> getExecutorAllTasks(String id) {
        Executor executor = (Executor) executorDao.read(id);
        return executor.getTasks();
    }

    public void updateStatus(String id, String status) {
        executorDao.updateStatus(id, status);
    }
}
