package com.itt.controllers;

import com.itt.entity.Executor;
import com.itt.service.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/exec")
public class ExecutorController {

    private ExecutorService executorService;

    @Autowired
    public ExecutorController (ExecutorService executorService) {
        this.executorService = executorService;
    }

    @RequestMapping(value = "/create/new", method = RequestMethod.GET)
    public String getCreateExecutorForm(Model model) {
        model.addAttribute("executorModel", new Executor());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "createExec";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createExecutorFromForm(@ModelAttribute("executorModel")Executor executor, Model model) {
        executorService.createExecutor(executor);
        return "redirect:/home";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String getUpdateExecutorForm(@PathVariable String id, Model model) {
        model.addAttribute("executorModel", executorService.getExecutorById(id));
        model.addAttribute("executors", executorService.getAllExecutors());
        return "updateExec";
    }

    @RequestMapping(value = "/update/", method = RequestMethod.POST)
    public String updateExecutorFromForm(@ModelAttribute("executorModel") Executor executor) {
        executorService.editExecutor(executor);
        return "redirect:/home";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteExecutor(@PathVariable String id) {
        executorService.deleteExecutorById(id);
        return "redirect:/home";
    }
}
