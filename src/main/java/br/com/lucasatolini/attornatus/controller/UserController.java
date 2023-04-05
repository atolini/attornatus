package br.com.lucasatolini.attornatus.controller;

import br.com.lucasatolini.attornatus.model.User;
import br.com.lucasatolini.attornatus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> edit(@PathVariable Long id) {
        return ResponseEntity.ok("edit" + id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok("get id" + id);
    }

    @GetMapping
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok("get list");
    }
}

