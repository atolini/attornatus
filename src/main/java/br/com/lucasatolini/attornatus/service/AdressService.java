package br.com.lucasatolini.attornatus.service;

import br.com.lucasatolini.attornatus.model.Adress;
import br.com.lucasatolini.attornatus.repository.AdressRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdressService {

    @Autowired
    private Validator validator;

    @Autowired
    private AdressRepository adressRepository;

    public AdressService(Validator validator, AdressRepository adressRepository) {
        this.validator = validator;
        this.adressRepository = adressRepository;
    }

    public Adress save(Adress adress) {
        Set<ConstraintViolation<Adress>> violations = validator.validate(adress);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Adress> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), null, null);
        }

        return this.adressRepository.save(adress);
    }
}
