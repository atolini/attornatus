package br.com.lucasatolini.attornatus.controller;

import br.com.lucasatolini.attornatus.controller.vo.UserVO;
import br.com.lucasatolini.attornatus.model.Address;
import br.com.lucasatolini.attornatus.model.User;
import br.com.lucasatolini.attornatus.repository.AddressRepository;
import br.com.lucasatolini.attornatus.repository.UserRepository;
import br.com.lucasatolini.attornatus.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IntegrationUserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void before() {
        this.userRepository.deleteAll();
    }

    @Test
    void create_user_success() {
        // request
        ResponseEntity<UserVO> response = restTemplate.postForEntity("/user/new",
                new UserVO("Lucas", Calendar.getInstance().getTime()),
                UserVO.class);

        // fetching
        Long idGenerated = Objects.requireNonNull(response.getBody()).getId();
        Optional<User> userRecovered = userService.getUserById(idGenerated);

        // testing
        assertTrue(userRecovered.isPresent());
        assertEquals(idGenerated, userRecovered.get().getId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // delete
        deleteUser(userRecovered.get().getId());
    }

    @Test
    void create_user_without_birthdate_error() {

        // request
        ResponseEntity<CustomRestException> response = restTemplate.postForEntity("/user/new",
                new UserVO("Lucas", null),
                CustomRestException.class);

        CustomRestException error = response.getBody();

        // testing
        assert error != null;
        assertEquals("Please, check field errors.", error.getMessage());
        assertEquals("birthDate: Birth date must not be blank", error.getErrors().get(0));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_user_without_name_error() {

        // request
        ResponseEntity<CustomRestException> response = restTemplate.postForEntity("/user/new",
                new UserVO(null, Calendar.getInstance().getTime()),
                CustomRestException.class);

        CustomRestException error = response.getBody();

        // testing
        assert error != null;
        assertEquals("Please, check field errors.", error.getMessage());
        assertEquals("name: Name must not be blank", error.getErrors().get(0));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void create_address_without_street_error() {
        Long id = createUser();

        // request
        final String URI = "/user/create-address/" + id;
        ResponseEntity<CustomRestException> response = restTemplate.postForEntity(URI,
                new Address(null, "06445550", "14A", "Barueri"),
                CustomRestException.class);

        CustomRestException error = response.getBody();

        // testing
        assert error != null;
        assertEquals("Please, check field errors.", error.getMessage());
        assertEquals("street: Adress must not be blank", error.getErrors().get(0));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // delete
        deleteUser(id);
    }

    @Test
    void create_address_non_existent_user_error() {
        Long id = 100L;

        // request
        final String URI = "/user/create-address/" + id;
        ResponseEntity<CustomRestException> response = restTemplate.postForEntity(URI,
                new Address("Rua A", "06445550", "14A", "Barueri"),
                CustomRestException.class);

        CustomRestException error = response.getBody();

        // testing
        assert error != null;
        assertEquals("Error - Entity not found.", error.getMessage());
        assertEquals("Non-existent user", error.getErrors().get(0));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // delete
        deleteUser(id);
    }

    @Test
    void delete_non_existent_address_error() {
        Long id = createUser();

        // request
        final String DELETE_URI = "/user/delete-address/" + id + "/" + "100";

        // request
        ResponseEntity<CustomRestException> response = restTemplate.exchange(DELETE_URI,
                HttpMethod.DELETE, null, CustomRestException.class);

        CustomRestException error = response.getBody();

        // testing
        assert error != null;
        assertEquals("Error - Entity not found.", error.getMessage());
        assertEquals("Non-existent address", error.getErrors().get(0));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void delete_non_existent_user_address_error() {
        Long id = 100L;

        // request
        final String DELETE_URI = "/user/delete-address/" + id + "/" + "100";

        // request
        ResponseEntity<CustomRestException> response = restTemplate.exchange(DELETE_URI,
                HttpMethod.DELETE, null, CustomRestException.class);

        CustomRestException error = response.getBody();

        // testing
        assert error != null;
        assertEquals("Error - Entity not found.", error.getMessage());
        assertEquals("Non-existent user", error.getErrors().get(0));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void get_user_success() {
        Long id = createUser();

        final String URI = "/user/" + id;

        ResponseEntity<User> response = restTemplate.getForEntity(URI, User.class);

        // testing
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // delete
        deleteUser(id);
    }

    @Test
    void get_non_existing_user_error() {
        Long id = 100L;

        final String URI = "/user/" + id;

        ResponseEntity<CustomRestException> response = restTemplate.getForEntity(URI, CustomRestException.class);

        // testing
        CustomRestException error = response.getBody();
        assertNotNull(error);
        assertEquals("Error - Entity not found.", error.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /* helpers */
    @Transactional
    private Long createUser() {
        User user = new User();
        user.setName("Lucas");
        user.setBirthDate(Calendar.getInstance().getTime());
        User saved = this.userRepository.save(user);
        return saved.getId();
    }

    @Transactional
    private void deleteUser(Long id) {
        Optional<User> fetched = this.userRepository.findById(id);
        fetched.ifPresent(user -> this.userRepository.delete(user));
    }

    @Transactional
    private void deleteAddress(Long id) {
        Optional<Address> fetched = this.addressRepository.findById(id);
        fetched.ifPresent(address -> this.addressRepository.delete(address));
    }
}