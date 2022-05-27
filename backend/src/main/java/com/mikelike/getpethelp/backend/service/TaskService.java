package com.mikelike.getpethelp.backend.service;

import com.mikelike.getpethelp.backend.domain.Task;
import com.mikelike.getpethelp.backend.domain.TaskInfo;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAll();
    Task findById(Long id);
    Task addWorker(Long id);
    Task chooseWorker(Long id,Long workerId);
    List<Task> getCurrentTasks();
    Task addTask(TaskInfo taskInfo);

}
