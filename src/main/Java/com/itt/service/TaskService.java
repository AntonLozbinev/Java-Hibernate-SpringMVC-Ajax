package com.itt.service;

import com.itt.dao.GenericDao;
import com.itt.dao.entitiesdao.ITaskDao;
import com.itt.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.util.List;

public class TaskService {

    @Autowired
    @Qualifier("taskDao")
    private GenericDao taskDao;

    public Task getTaskById(Serializable id) {
        return (Task) taskDao.read(id);
    }

    public void deleteTaskById(Serializable id) {
        taskDao.delete(id);
    }

    public void createTask(Task task) {
        taskDao.create(task);
    }

    public void editTask(Task task) {
        taskDao.update(task);
    }

    public List<Task> getAllTasks() {
        return taskDao.getAll();
    }

    public void updateStatus(Integer id, String status) {
        taskDao.updateStatus(id, status);
    }

    public List<Task> getTasksWithMaxPriorityForExecutor() {
        return ((ITaskDao)taskDao).getTasksWithMaxPriorityForExecutor();
    }



}
