package org.example.backend.ErrorArchive.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private ErrorArchive errorArchive;

    //@ManyToOne
   // @Column(nullable = false)
   // private User user_id;

    @Column(nullable = false)
    private boolean state;
}
