package com.openclassrooms.mddapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255, message = "Le titre ne doit pas dépasser 255 caractères.")
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 2000, message = "La description ne doit pas dépasser 2000 caractères.")
    @Column(name = "description", nullable = false)
    private String description;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // relations
    @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
    private List<Post> posts;
}
