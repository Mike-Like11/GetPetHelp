package com.mikelike.getpethelp.backend.dao;

import com.mikelike.getpethelp.backend.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker,Long> {
   Worker findByShortUserInfo(ShortUserInfo shortUserInfo);
}
