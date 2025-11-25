package com.example.email_notifier_backend.Entity;


//import com.example.email_notifier_backend.util.Role;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "users")
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable=false)
//    private String name;
//
//    @Column(nullable=false, unique=true)
//    private String email;
//
//    @Column(nullable=false)
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable=false)
//    private Role role = Role.ROLE_USER;
//
//    private boolean enabled = true;
//
//    // For password reset
//    private String resetToken;
//    private Long resetTokenExpiry; // epoch millis
//}


import com.example.email_notifier_backend.util.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role ;

    @OneToMany(mappedBy = "user")
    private Set<Events> events;
}
