package br.com.lucasatolini.attornatus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_user")
public class User extends RepresentationModel<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, length = 100)
    @NotNull(message = "Name must not be blank")
    @Size(max = 100, message = "Name should not exceed 100 characters, please use abbreviations")
    private String name;

    @Column(nullable = false, updatable = false)
    @NotNull(message = "Birth date must not be blank")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToOne(cascade = CascadeType.ALL)
    private Address mainAddress;

    public User() {
        this.addresses = new ArrayList<>();
    }

    public User(String nome, Date birthDate, Address mainAddress) {
        this.name = nome;
        this.birthDate = birthDate;
        this.mainAddress = mainAddress;
        this.addresses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Address getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address mainAddress) {
        this.mainAddress = mainAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }
}
