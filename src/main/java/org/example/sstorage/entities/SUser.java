package org.example.sstorage.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
@Entity
@Table(name = "suser")
public class SUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private UserRole role;
}
