package com.mikelike.getpethelp.backend.dao;


import com.mikelike.getpethelp.backend.domain.ShortUserInfo;
import com.mikelike.getpethelp.backend.domain.Task;
import com.mikelike.getpethelp.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository  extends JpaRepository<Task,Long> {
    @Query(value = "SELECT * from task  where worker_id is null",nativeQuery = true)
    List<Task> findAllFree();
    List<Task> findAllByShortUserInfo(ShortUserInfo shortUserInfo);
}
