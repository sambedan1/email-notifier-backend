package com.example.email_notifier_backend.Controller;

import com.example.email_notifier_backend.Dto.UserDTO;
import com.example.email_notifier_backend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {

        return userService.update(id, dto);
    }
//    @PostMapping
//    public UserDTO createUser(@RequestBody UserDTO dto) {
//        return userService.create(dto);
//    }

}
