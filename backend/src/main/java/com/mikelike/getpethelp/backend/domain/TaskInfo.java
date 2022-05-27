package com.mikelike.getpethelp.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name="task_info")
public class TaskInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String name;

    @Override
    public String toString() {
        return "TaskInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateOfPerformance='" + dateOfPerformance + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    private String description;
    private String dateOfPerformance;
    private Double latitude;
    private Double longitude;
    @OneToOne
    @JoinColumn(name = "task_id", nullable = true)
    @JsonIgnore
    private Task task;
}
