package com.itt.controllers;

import com.itt.entity.Task;
import com.itt.service.ExecutorService;
import com.itt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

    private ExecutorService executorService;
    private TaskService taskService;

    @Autowired
    public HomeController(ExecutorService executorService, TaskService taskService) {
        this.executorService = executorService;
        this.taskService = taskService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showHomePage(Model model) {
        model.addAttribute("executors", executorService.getAllExecutors());
        model.addAttribute("tasks", taskService.getAllTasks());
        return "home";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showHomePageWithExecutorTasks(@PathVariable String id, Model model) {
        model.addAttribute("executors", executorService.getAllExecutors());
        model.addAttribute("tasks", executorService.getExecutorAllTasks(id));
        return "home";
    }

    @RequestMapping(value = "/sort/{sortType}", method = RequestMethod.GET)
    public String sortTasks(@PathVariable String sortType, Model model) {
        List<Task> tasks = taskService.getAllTasks();
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (sortType.equals("up")) {
                    return o1.getPriority() - o2.getPriority();
                } else {
                    return o2.getPriority() - o1.getPriority();
                }
            }
        });
        model.addAttribute("executors", executorService.getAllExecutors());
        model.addAttribute("tasks", tasks);
        return "home";
    }

    @RequestMapping(value = "/ajax", method = RequestMethod.GET)
    public @ResponseBody List<Task> doAjax() throws IOException {
        return taskService.getAllTasks();
    }
}
