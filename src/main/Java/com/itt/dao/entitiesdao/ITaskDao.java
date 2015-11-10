package com.itt.dao.entitiesdao;

import com.itt.entity.Task;

import java.util.List;

public interface ITaskDao {

    List<Task> getTasksWithMaxPriorityForExecutor();
}
