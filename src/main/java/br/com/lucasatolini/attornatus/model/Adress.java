package br.com.lucasatolini.attornatus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

@Entity(name = "adress")
@Table(name = "tb_adress")
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, length = 100)
    @NotNull(message = "Adress must not be blank")
    @Max(message = "Adress should not exceed 100 characters, please use abbreviations", value = 100)
    private String logradouro;

    @Column(nullable = false, updatable = false, length = 8)
    @NotNull(message = "Zip code must not be blank")
    @Max(message = "Zip code should not exceed 8 characters", value = 8)
    private Long CEP;

    @Column(nullable = false, updatable = false, length = 10)
    @NotNull(message = "House number must not be blank")
    @Max(message = "House number should not exceed 10 characters", value = 10)
    private String num;

    @Column(nullable = false, updatable = false, length = 100)
    @NotNull(message = "City must not be blank")
    @Max(message = "City should not exceed 100 characters, please use abbreviations", value = 100)
    private String cidade;

    public Adress() {}

    public Adress(String logradouro, Long CEP, String num, String cidade) {
        this.logradouro = logradouro;
        this.CEP = CEP;
        this.num = num;
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Long getCEP() {
        return CEP;
    }

    public void setCEP(Long CEP) {
        this.CEP = CEP;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
