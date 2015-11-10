package com.itt.controllers;

import com.itt.TaskHandler;
import com.itt.entity.Task;
import com.itt.service.ExecutorService;
import com.itt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/task")
public class TaskController {

    private TaskService taskService;
    private ExecutorService executorService;
    @Autowired
    private TaskHandler taskHandler;

    @Autowired
    public TaskController(TaskService taskService, ExecutorService executorService) {
        this.executorService = executorService;
        this.taskService = taskService;
    }

    @RequestMapping(value = "/create/new", method = RequestMethod.GET)
    public String getCreateTaskForm(Model model) {
        model.addAttribute("taskModel", new Task());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "createTask";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createTaskFromForm(@ModelAttribute("taskModel") Task task, @RequestParam String executorId) {
        if (!executorId.equals("none")) {
            task.setExecutor(executorService.getExecutorById(executorId));
        }
        taskService.createTask(task);
        return "redirect:/home";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String getUpdateTaskForm(@PathVariable Integer id, Model model) {
        model.addAttribute("executors", executorService.getAllExecutors());
        model.addAttribute("taskModel", taskService.getTaskById(id));
        return "updateTask";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateTaskFromForm(@ModelAttribute("executorModel") Task task, @RequestParam String executorId) {
        if ( (task.getExecutor() == null || !task.getExecutor().getName().equals(executorId)) && !executorId.equals("none") ) {
            task.setExecutor(executorService.getExecutorById(executorId));
        }
        taskService.editTask(task);
        return "redirect:/home";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteTask(@PathVariable Integer id) {
        taskService.deleteTaskById(id);
        return "redirect:/home";
    }

    @RequestMapping(value = "/start/{id}", method = RequestMethod.GET)
    public @ResponseBody String startTask(@PathVariable Integer id) {
        taskService.updateStatus(id, "running");
        return taskHandler.forcedTaskStart(id);
    }
}
