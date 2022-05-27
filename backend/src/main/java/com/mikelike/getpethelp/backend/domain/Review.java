package com.mikelike.getpethelp.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private ShortUserInfo user;
    private String message;
    private Double rating;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Аналогично с полями книги
    @JoinColumn(name = "worker_id")
    @JsonIgnore
    private Worker worker;

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", message='" + message + '\'' +
                ", rating=" + rating +
                ", date='" + date + '\'' +
                '}';
    }

    private String date = new Date().toLocaleString();
}
