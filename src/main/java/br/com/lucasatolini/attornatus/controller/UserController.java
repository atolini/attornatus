package br.com.lucasatolini.attornatus.controller;

import br.com.lucasatolini.attornatus.config.AuthenticatedUser;
import br.com.lucasatolini.attornatus.controller.vo.UserVO;
import br.com.lucasatolini.attornatus.model.Address;
import br.com.lucasatolini.attornatus.model.User;
import br.com.lucasatolini.attornatus.repository.UserRepository;
import br.com.lucasatolini.attornatus.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository repo;

    /* POST - CREATE METHODS */
    @PostMapping("/create-address/{id}")
    public ResponseEntity<User> createAddress(@PathVariable Long id, @Valid @RequestBody Address address) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createAddress(id, address));
    }

    /* DELETE METHODS */
    @DeleteMapping("/delete-address/{userId}/{addressId}")
    public ResponseEntity<User> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        return ResponseEntity.ok(this.userService.deleteAddress(userId, addressId));
    }

    /* GET METHODS */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userFetched = this.userService.getUserById(id);
        if (userFetched.isPresent()) {
            return ResponseEntity.ok(userFetched.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping("logout")
    public ResponseEntity<Object> logout() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) securityContext.getAuthentication();
        User user = (User) authenticatedUser.getPrincipal();
        this.userService.setAuthenticated(false, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/get-all/{pageNumber}/{pageSize}")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        Page<User> users = this.userService.getAll(pageNumber, pageSize);
        return ResponseEntity.ok(users.getContent());
    }

    /* PUT - UPDATE METHODS */
    @PutMapping("/detach-main-address/{id}")
    public ResponseEntity<User> detachMainAddress(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.detachMainAddress(id));
    }

    @PutMapping("/attach-main-address/{userId}/{addressId}")
    public ResponseEntity<User> attachMainAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        return ResponseEntity.ok(this.userService.attachMainAddress(userId, addressId));
    }

    @PutMapping("/edit")
    public ResponseEntity<User> edit(@RequestBody UserVO user) {
        User userUpdated = this.userService.edit(user);
        return ResponseEntity.ok(userUpdated);
    }
}
