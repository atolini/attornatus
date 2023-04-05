package br.com.lucasatolini.attornatus.service;

import br.com.lucasatolini.attornatus.model.User;
import br.com.lucasatolini.attornatus.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private Validator validator;

    @Autowired
    private UserRepository userRepository;

    public UserService(Validator validator, UserRepository userRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
    }

    public User save(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<User> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), null, null);
        }

        return this.userRepository.save(user);
    }
}
