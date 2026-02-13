package com.dev.encurtadorURL.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String urlOriginal;

    @Column(nullable = false, unique = true)
    private String urlEncurtada;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        expirationDate = createdAt.plusDays(30);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }
}
