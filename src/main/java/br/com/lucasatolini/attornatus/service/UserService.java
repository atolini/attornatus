package br.com.lucasatolini.attornatus.service;

import br.com.lucasatolini.attornatus.controller.vo.UserVO;
import br.com.lucasatolini.attornatus.model.Address;
import br.com.lucasatolini.attornatus.model.User;
import br.com.lucasatolini.attornatus.repository.AddressRepository;
import br.com.lucasatolini.attornatus.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class UserService {

    private final String nonExistentMessage = "Non-existent user";

    @Autowired
    private final Validator validator;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public UserService(Validator validator, UserRepository userRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
    }

    public User save(@Valid UserVO user) {
        User userToSave = new User();
        userToSave.setBirthDate(user.getBirthDate());
        userToSave.setName(user.getName());
        return this.userRepository.save(userToSave);
    }

    public Optional<User> getUserById(Long id) {
        return this.userRepository.findById(id);
    }

    public User edit(@Valid UserVO user) {
        if (user.getId() != null) {
            this.userRepository.update(user.getName(), user.getBirthDate(), user.getId());

            Optional<User> userUpdated = this.userRepository.findById(user.getId());

            if (userUpdated.isPresent()) {
                return userUpdated.get();
            }
        }

        throw new EntityNotFoundException("");
    }

    public User createAddress(Long id, Address address) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if (userOptional.isPresent()) {
            User userFetched = userOptional.get();
            userFetched.addAddress(address);
            return this.userRepository.save(userFetched);
        }

        throw new EntityNotFoundException(nonExistentMessage);
    }

    public User attachMainAddress(Long userId, Long addressId) {
        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User userFetched = userOptional.get();
            List<Address> addresses = userFetched.getAddresses();

            for (Address address : addresses) {
                if (Objects.equals(address.getId(), addressId)) {
                    userFetched.setMainAddress(address);
                    this.userRepository.save(userFetched);
                }
            }

            return userFetched;
        }

        throw new EntityNotFoundException();
    }

    public User detachMainAddress(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if (userOptional.isPresent()) {
            User userFetched = userOptional.get();
            userFetched.setMainAddress(null);
            this.userRepository.save(userFetched);
            return userFetched;
        }

        throw new EntityNotFoundException();
    }

    public User deleteAddress(Long userId, Long addressId) {
        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User userFetched = userOptional.get();
            List<Address> addresses = userFetched.getAddresses();
            Address addressToDelete = null;

            for (Address address : addresses) {
                if (Objects.equals(address.getId(), addressId)) {
                    addressToDelete = address;
                }
            }

            if (addressToDelete != null) {
                if (userFetched.getMainAddress() != null && userFetched.getMainAddress().equals(addressToDelete)) {
                    userFetched.setMainAddress(null);
                }
                userFetched.removeAddress(addressToDelete);
                this.userRepository.save(userFetched);
                this.addressRepository.delete(addressToDelete);
            } else {
                throw new EntityNotFoundException("Non-existent address");
            }

            return userFetched;
        }

        throw new EntityNotFoundException(nonExistentMessage);
    }

    public Page<User> getAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return this.userRepository.findAll(pageable);
    }
}
