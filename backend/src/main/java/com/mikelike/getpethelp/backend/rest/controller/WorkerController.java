package com.mikelike.getpethelp.backend.rest.controller;

import com.mikelike.getpethelp.backend.domain.Review;
import com.mikelike.getpethelp.backend.domain.Task;
import com.mikelike.getpethelp.backend.domain.Worker;
import com.mikelike.getpethelp.backend.domain.WorkerInfo;
import com.mikelike.getpethelp.backend.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkerController {
    private final WorkerService workerService;
    @GetMapping("/user/worker")
    public Worker getCurrentWorker(){
        return workerService.getCurrentWorker();
    }
    @GetMapping("/user/worker/tasks")
    public List<Task> getCurrentWorkerTasks(){
        return workerService.getCurrentWorkerTasks();
    }
    @PostMapping("/user/worker")
    public Worker createWorker(@RequestBody WorkerInfo workerInfo){
        return workerService.createCv(workerInfo);
    }

    @GetMapping("/workers")
    public List<Worker> getAllWorkers(){
        return workerService.getAllWorkers();
    }
    @PostMapping("/workers/{id}")
    public Worker addReview(@RequestBody Review review, @PathVariable Long id){
        return workerService.addReview(review,id);
    }
}
