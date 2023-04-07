package br.com.lucasatolini.attornatus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

@Entity(name = "adress")
@Table(name = "tb_adress")
public class Address extends RepresentationModel<Address> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, length = 100)
    @NotNull(message = "Adress must not be blank")
    @Size(message = "Adress should not exceed 100 characters, please use abbreviations", max = 100)
    private String street;

    @Column(nullable = false, updatable = false, length = 8)
    @NotNull(message = "Zip code must not be blank")
    @Size(max=8, min=7, message = "Zip code must ")
    private String zipCode;

    @Column(nullable = false, updatable = false, length = 10)
    @NotNull(message = "House number must not be blank")
    @Size(message = "House number should not exceed 10 characters", max = 10)
    private String number;

    @Column(nullable = false, updatable = false, length = 100)
    @NotNull(message = "City must not be blank")
    @Size(message = "City should not exceed 100 characters, please use abbreviations", max = 100)
    private String city;

    public Address() {}

    public Address(String street, String zipCode, String number, String city) {
        this.street = street;
        this.zipCode = zipCode;
        this.number = number;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Address address = (Address) o;

        if (!id.equals(address.id)) return false;
        if (!street.equals(address.street)) return false;
        if (!zipCode.equals(address.zipCode)) return false;
        if (!number.equals(address.number)) return false;
        return city.equals(address.city);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + zipCode.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + city.hashCode();
        return result;
    }
}
