package br.com.lucasatolini.attornatus.controller;

import br.com.lucasatolini.attornatus.config.AuthenticatedUser;
import br.com.lucasatolini.attornatus.config.JwtTokenUtil;
import br.com.lucasatolini.attornatus.controller.vo.LoginVO;
import br.com.lucasatolini.attornatus.controller.vo.UserVO;
import br.com.lucasatolini.attornatus.model.User;
import br.com.lucasatolini.attornatus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/public")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<User> create(@RequestBody @Valid UserVO user) {
        User userCreated = this.userService.save(user);
        Long id = userCreated.getId();
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel();
        userCreated.add(selfLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginVO user) {
       try {
           AuthenticatedUser authenticated = (AuthenticatedUser) authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           user.getUserName(), user.getPassword()));

           User userAuthenticated = (User) authenticated.getPrincipal();

           this.userService.setAuthenticated(true, userAuthenticated);

           return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,
                   jwtTokenUtil.generateToken(userAuthenticated))
                   .build();
       } catch (BadCredentialsException ex) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       }
    }
}
