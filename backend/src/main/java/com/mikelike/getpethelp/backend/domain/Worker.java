package com.mikelike.getpethelp.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="worker")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToOne(cascade = CascadeType.ALL,mappedBy="worker")
    private WorkerInfo workerInfo;
    private Double rating = 0.0;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private ShortUserInfo shortUserInfo;
    @Transient
    @ManyToMany(mappedBy = "workerInfoList")
    private List<Task> tasks = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "worker")
    private List<Review> reviews = new ArrayList<Review>();
}
