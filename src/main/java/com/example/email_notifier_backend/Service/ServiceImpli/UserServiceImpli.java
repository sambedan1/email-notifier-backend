////package com.example.email_notifier_backend.Service.ServiceImpli;
////
////import com.example.email_notifier_backend.Dto.UserDTO;
////import com.example.email_notifier_backend.Entity.User;
////import com.example.email_notifier_backend.Repository.UserRepository;
////import com.example.email_notifier_backend.Service.UserService;
////import com.example.email_notifier_backend.util.Role;
////import lombok.AllArgsConstructor;
////import org.modelmapper.ModelMapper;
//////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.stereotype.Service;
////
////@AllArgsConstructor
////@Service
////public class UserServiceImpli implements UserService {
////    private final UserRepository userRepo;
////    //private final PasswordEncoder passwordEncoder;
////    private final ModelMapper mapper;
////
////    @Override
////    public UserDTO register(UserDTO dto) {
////        User user = mapper.map(dto, User.class);
////
////       // user.setPassword(passwordEncoder.encode(dto.getPassword()));
////        user.setRole(Role.valueOf("USER")); // default role
////
////        user = userRepo.save(user);
////        return mapper.map(user, UserDTO.class);
////    }
////
////    @Override
////    public UserDTO login(String email, String password) {
////        User user = userRepo.findByEmail(email)
////                .orElseThrow(() -> new RuntimeException("Invalid email"));
////
//////        if (!passwordEncoder.matches(password, user.getPassword())) {
//////            throw new RuntimeException("Invalid password");
//////        }
////
////        return mapper.map(user, UserDTO.class);
////    }
////
////    @Override
////    public String generatePasswordResetToken(String email) {
////        return "";
////    }
////
////    @Override
////    public String resetPassword(String token, String newPassword) {
////        return "";
////    }
////
////    @Override
////    public UserDTO getById(Long id) {
////        User user = userRepo.findById(id)
////                .orElseThrow(() -> new RuntimeException("User not found"));
////
////        return mapper.map(user, UserDTO.class);
////    }
////
////    @Override
////    public UserDTO update(Long id, UserDTO dto) {
////        User user = userRepo.findById(id)
////                .orElseThrow(() -> new RuntimeException("User not found"));
////
////        if (dto.getName() != null)
////            user.setName(dto.getName());
////
////        if (dto.getPassword() != null)
////            user.setPassword(passwordEncoder.encode(dto.getPassword()));
////
////        user = userRepo.save(user);
////
////        return mapper.map(user, UserDTO.class);
////    }
////}
//package com.example.email_notifier_backend.Service.ServiceImpli;
//
//import com.example.email_notifier_backend.Dto.UserDTO;
//import com.example.email_notifier_backend.Entity.User;
//import com.example.email_notifier_backend.Repository.UserRepository;
//import com.example.email_notifier_backend.Service.UserService;
//import com.example.email_notifier_backend.util.Role;
//import lombok.AllArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//
//@AllArgsConstructor
//@Service
//public class UserServiceImpli implements UserService {
//    private final UserRepository userRepo;
//    private final ModelMapper mapper;
//
//    @Override
//    public UserDTO register(UserDTO dto) {
//        if (userRepo.existsByEmail(dto.getEmail())) {
//            throw new RuntimeException("Email already registered");
//        }
//
//        User user = new User();
//        user.setName(dto.getName());
//        user.setEmail(dto.getEmail());
//        user.setPassword(dto.getPassword()); // plain text for now
//        user.setRole(Role.ROLE_USER);
//        user.setEnabled(true);
//
//        user = userRepo.save(user);
//
//        UserDTO out = mapper.map(user, UserDTO.class);
//        //out.setPassword(null); // do not return password
//        return out;
//    }
//
//    @Override
//    public UserDTO login(String email, String password) {
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Invalid email"));
//
//        if (!user.getPassword().equals(password)) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        UserDTO out = mapper.map(user, UserDTO.class);
//        //out.setPassword(null);
//        return out;
//    }
//
//    @Override
//    public String generatePasswordResetToken(String email) {
//        // placeholder - implement later
//        return "";
//    }
//
//    @Override
//    public String resetPassword(String token, String newPassword) {
//        // placeholder - implement later
//        return "";
//    }
//
//    @Override
//    public UserDTO getById(Long id) {
//        User user = userRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        UserDTO out = mapper.map(user, UserDTO.class);
//        //out.setPassword(null);
//        return out;
//    }
//
//    @Override
//    public UserDTO update(Long id, UserDTO dto) {
//        User user = userRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (dto.getName() != null) user.setName(dto.getName());
//        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
//            if (userRepo.existsByEmail(dto.getEmail())) {
//                throw new RuntimeException("Email already in use");
//            }
//            user.setEmail(dto.getEmail());
//        }
//        // do not update password here unless you want to allow it
//        user = userRepo.save(user);
//
//        UserDTO out = mapper.map(user, UserDTO.class);
//        //out.setPassword(null);
//        return out;
//    }
//}
package com.example.email_notifier_backend.Service.ServiceImpli;

import com.example.email_notifier_backend.Dto.AuthRequestDTO;
import com.example.email_notifier_backend.Dto.AuthResponseDTO;
import com.example.email_notifier_backend.Dto.UserDTO;
import com.example.email_notifier_backend.Dto.UserResponseDTO;
import com.example.email_notifier_backend.Entity.User;
import com.example.email_notifier_backend.Repository.UserRepository;
import com.example.email_notifier_backend.Security.JwtUtil;
import com.example.email_notifier_backend.Service.UserService;
import com.example.email_notifier_backend.util.Role;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpli implements UserService {

    @Autowired private UserRepository userRepo;
    @Autowired private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO register(AuthRequestDTO dto) {
        if(userRepo.findByEmail(dto.getEmail()).isPresent())
            throw new RuntimeException("Email already exists");
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ROLE_USER);
        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token, user.getId(), user.getName(), user.getEmail(), user.getRole().toString());
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {
        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token, user.getId(), user.getName(), user.getEmail(), user.getRole().toString());
    }

    @Override
    public UserResponseDTO getProfile(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public Long getUserIdFromEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }
}


