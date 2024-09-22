package org.example.backend.ErrorArchive.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.User.Model.Entity.User;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ErrorScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "error_archive_id", nullable = false)
    private ErrorArchive errorArchive;

}
