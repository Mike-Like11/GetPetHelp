package com.mikelike.getpethelp.backend.service;

import com.mikelike.getpethelp.backend.dao.TaskRepository;
import com.mikelike.getpethelp.backend.dao.WorkerRepository;
import com.mikelike.getpethelp.backend.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;
    private final UserService userService;
    private final TaskRepository taskRepository;

    @Override
    public Worker createCv(WorkerInfo workerInfo) {
        User currentUser = userService.getCurrentUser();
        WorkerInfo newWorkerInfo = WorkerInfo.builder()
                .experience(workerInfo.getExperience())
                .preferences(workerInfo.getPreferences())
                .build();
        Worker worker = new Worker();
        newWorkerInfo.setWorker(worker);
        worker.setWorkerInfo(newWorkerInfo);
        worker.setShortUserInfo(currentUser.getShortUserInfo());
        return workerRepository.save(worker);
    }

    @Override
    public Worker findById(Long id) {
        Optional<Worker> worker = workerRepository.findById(id);
        return worker.get();
    }

    @Override
    public Worker addReview(Review review, Long id) {
        Optional<Worker> worker = workerRepository.findById(id);
        if(worker.isPresent()){
            Review newReview = Review.builder()
                    .message(review.getMessage())
                    .rating(review.getRating())
                    .worker(worker.get())
                    .user(userService.getCurrentUser().getShortUserInfo())
                    .build();
            worker.get().getReviews().add(review);
            final double[] sum = {0.0};
            worker.get().getReviews().forEach(review1 -> sum[0] +=review1.getRating());
            worker.get().setRating(sum[0] /worker.get().getReviews().size());
            workerRepository.save(worker.get());
        }
        return worker.get();
    }

    @Override
    public List<Task> getCurrentWorkerTasks() {
       return getCurrentWorker().getTasks();
    }

    @Override
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    @Override
    public Worker getCurrentWorker() {
        User currentUser = userService.getCurrentUser();
        return workerRepository.findByShortUserInfo(currentUser.getShortUserInfo());
    }
}
