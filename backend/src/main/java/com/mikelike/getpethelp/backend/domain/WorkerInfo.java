package com.mikelike.getpethelp.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="worker_info")
public class WorkerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String experience;
    private String preferences;

    @Override
    public String toString() {
        return "WorkerInfo{" +
                "id=" + id +
                ", experience='" + experience + '\'' +
                ", preferences='" + preferences + '\'' +
                '}';
    }

    @OneToOne
    @JoinColumn(name = "worker_id", nullable = true)
    @JsonIgnore
    private Worker worker;
}
