package com.mikelike.getpethelp.backend.rest.controller;
import com.mikelike.getpethelp.backend.domain.Task;
import com.mikelike.getpethelp.backend.domain.TaskInfo;
import com.mikelike.getpethelp.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> getTask(){
        return taskService.getAll();
    }

    @GetMapping("/tasks/{taskId}")
    public Task getTaskById(@PathVariable Long taskId){
        return taskService.findById(taskId);
    }

    @GetMapping("/tasks/{taskId}/respond")
    public Task getRespond(@PathVariable Long taskId){
        return taskService.addWorker(taskId);
    }
    @GetMapping("/user/tasks")
    public List<Task> getUserTask(){
        System.out.println(taskService.getCurrentTasks().toString());
        return taskService.getCurrentTasks();
    }
    @PostMapping("/user/tasks")
    public Task addTask(@RequestBody TaskInfo taskInput){
        return taskService.addTask(taskInput);
    }
}
