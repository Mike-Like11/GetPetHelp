package com.mikelike.getpethelp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private ShortUserInfo shortUserInfo;
    @OneToOne(cascade = CascadeType.ALL,mappedBy="task")
    private TaskInfo taskInfo;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Worker> workerInfoList = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "worker_id", nullable = true)
    private Worker worker;
    private String dateOfCreation = new Date().toLocaleString();
}
