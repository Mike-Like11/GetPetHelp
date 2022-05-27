package com.mikelike.getpethelp.backend.service;

import com.mikelike.getpethelp.backend.domain.Review;
import com.mikelike.getpethelp.backend.domain.Task;
import com.mikelike.getpethelp.backend.domain.Worker;
import com.mikelike.getpethelp.backend.domain.WorkerInfo;

import java.util.List;

public interface WorkerService {
    Worker createCv(WorkerInfo workerInfo);
    Worker findById(Long id);
    Worker addReview(Review review, Long id);
    List<Task> getCurrentWorkerTasks();
    List<Worker> getAllWorkers();
    Worker getCurrentWorker();
}
