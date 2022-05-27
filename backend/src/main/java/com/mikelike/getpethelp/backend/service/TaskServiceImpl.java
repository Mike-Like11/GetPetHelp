package com.mikelike.getpethelp.backend.service;

import com.mikelike.getpethelp.backend.dao.TaskRepository;
import com.mikelike.getpethelp.backend.domain.Task;
import com.mikelike.getpethelp.backend.domain.TaskInfo;
import com.mikelike.getpethelp.backend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;
//    @Autowired
//    private WorkerService workerService;

    @Override
    public List<Task> getAll() {
        return taskRepository.findAllFree();
    }

    @Override
    public Task findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.get();
    }

    @Override
    public Task addWorker(Long id) {
        System.out.println(id);
        Optional<Task> task = taskRepository.findById(id);
//        if(task.isPresent()){
//            task.get().getWorkerInfoList().add(workerService.getCurrentWorker());
//            return taskRepository.save(task.get());
//        }
        return null;
    }

    @Override
    public Task chooseWorker(Long id, Long workerId) {
        System.out.println(workerId);
        Optional<Task> task = taskRepository.findById(id);
//        if(task.isPresent()){
//            task.get().setWorker(workerService.findById(workerId));
//            return taskRepository.save(task.get());
//        }
        return null;
    }

    @Override
    public List<Task> getCurrentTasks() {
        User currentUser = userService.getCurrentUser();
        return taskRepository.findAllByShortUserInfo(currentUser.getShortUserInfo());
    }

    @Override
    public Task addTask(TaskInfo taskInfo) {
        System.out.println(taskInfo);
        TaskInfo newTaskInfo = TaskInfo.builder().
                dateOfPerformance(taskInfo.getDateOfPerformance())
                .description(taskInfo.getDescription())
                .latitude(taskInfo.getLatitude())
                .longitude(taskInfo.getLongitude())
                .name(taskInfo.getName()).build();
        User currentUser = userService.getCurrentUser();
        Task task = new Task();
        task.setTaskInfo(newTaskInfo);
        task.setShortUserInfo(currentUser.getShortUserInfo());
        newTaskInfo.setTask(task);
        return taskRepository.save(task);
    }
}