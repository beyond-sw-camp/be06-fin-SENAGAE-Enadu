package org.example.backend.Wiki.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LatestWiki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false, length = 255)
    private String thumbnailImgUrl;


}
