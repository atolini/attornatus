package br.com.lucasatolini.attornatus.config;

import br.com.lucasatolini.attornatus.model.User;
import br.com.lucasatolini.attornatus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (name != null && password != null) {
            AuthenticatedUser userDetails = (AuthenticatedUser) userService.loadUserByUsername(name);

            if (Objects.equals(userDetails.getUsername(), name) &&
                    encoder.matches(password, userDetails.getPassword())) {
                userService.setAuthenticated(true, (User) userDetails.getPrincipal());
                return userDetails;
            }
        }

        throw new BadCredentialsException("Username/Password does not match for " + authentication.getPrincipal());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
