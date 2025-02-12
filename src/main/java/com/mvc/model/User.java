package com.mvc.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users", schema = "technology")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String role;

    @Column(nullable = false)
    private Boolean status;

}
