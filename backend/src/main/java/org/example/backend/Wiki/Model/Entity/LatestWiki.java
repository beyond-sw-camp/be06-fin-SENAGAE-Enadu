package org.example.backend.Wiki.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class LatestWiki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @LastModifiedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false, length = 255)
    private String thumbnailImgUrl;


    public void updateContentAndVersion(String content, int newVersion) {
        this.content = content;
        this.version = newVersion;
    }

    public void updateThumbnail(String thumbnailImgUrl) {
        this.thumbnailImgUrl = thumbnailImgUrl;
    }
}
