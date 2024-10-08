package com.example.common.EmailVerify.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class EmailVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private  String email;
    @Column(nullable = false, length = 100, unique = true)
    private String uuid;

    // UUID 업데이트 메소드
    public void updateUuid(String uuid){
        this.uuid = uuid;
    }


}
