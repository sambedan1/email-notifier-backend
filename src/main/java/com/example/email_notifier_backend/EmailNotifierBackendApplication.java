//package com.example.email_notifier_backend;
//
////import org.springframework.boot.SpringApplication;
////import org.springframework.boot.autoconfigure.SpringBootApplication;
////
////@SpringBootApplication
////public class EmailNotifierBackendApplication {
////
////	public static void main(String[] args) {
////		SpringApplication.run(EmailNotifierBackendApplication.class, args);
////	}
////
////}
//
//
//import com.example.email_notifier_backend.Entity.User;
//import com.example.email_notifier_backend.Repository.UserRepository;
//import com.example.email_notifier_backend.util.Role;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//@SpringBootApplication
//public class EmailNotifierBackendApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(EmailNotifierBackendApplication.class, args);
//    }
//
//    // CHANGE THESE VALUES as required — this is where admin email/password are stored.
//    private static final String ADMIN_EMAIL = "admin@example.com";
//    private static final String ADMIN_PASSWORD = "admin123"; // plain text for now as requested
//    private static final String ADMIN_NAME = "Admin User";
//
//    @Bean
//    CommandLineRunner initAdmin(UserRepository userRepository) {
//        return args -> {
//            userRepository.findByEmail(ADMIN_EMAIL).ifPresentOrElse(u -> {
//                System.out.println("Admin already exists: " + ADMIN_EMAIL);
//            }, () -> {
//                User admin = new User();
//                admin.setName(ADMIN_NAME);
//                admin.setEmail(ADMIN_EMAIL);
//                admin.setPassword(ADMIN_PASSWORD);
//                admin.setRole(Role.ROLE_ADMIN);
//                admin.setEnabled(true);
//                userRepository.save(admin);
//                System.out.println("Created admin user: " + ADMIN_EMAIL);
//            });
//        };
//    }
//}
//
package com.example.email_notifier_backend;

import com.example.email_notifier_backend.Entity.User;
import com.example.email_notifier_backend.Repository.UserRepository;
import com.example.email_notifier_backend.util.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EmailNotifierBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailNotifierBackendApplication.class, args);
    }

    // Admin account details
    private static final String ADMIN_EMAIL = "admin@example.com";
    private static final String ADMIN_PASSWORD = "admin123"; // plain text, will be encoded
    private static final String ADMIN_NAME = "Admin User";

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.findByEmail(ADMIN_EMAIL).ifPresentOrElse(u -> {
                System.out.println("Admin already exists: " + ADMIN_EMAIL);
            }, () -> {
                User admin = new User();
                admin.setName(ADMIN_NAME);
                admin.setEmail(ADMIN_EMAIL);
                admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
                admin.setRole(Role.ROLE_ADMIN); // Should resolve to "ROLE_ADMIN"
                userRepository.save(admin);
                System.out.println("Created admin user: " + ADMIN_EMAIL);
            });
        };
    }
}
