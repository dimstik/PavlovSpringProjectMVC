package ru.pavlov.springproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pavlov.springproject.entity.Task;
import ru.pavlov.springproject.services.TaskService;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public String findAllTasksPaged(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Model model) {
        Page<Task> taskPage = taskService.findAllTasks(PageRequest.of(page, size, Sort.by("id")));
        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("currentPage", taskPage.getNumber());
        model.addAttribute("totalPages", taskPage.getTotalPages());
        return "tasks/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        return "tasks/show";
    }

    @GetMapping("/new")
    public String newTask(@ModelAttribute("task") Task task, Model model) {
        model.addAttribute("status", taskService.getAllStatus());
        return "tasks/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "tasks/new";

        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("status", taskService.getAllStatus());
        return "tasks/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult, @PathVariable("id") Integer id) {
    if(bindingResult.hasErrors())
        return "tasks/edit";

    taskService.updateTask(id, task);
    return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
