package br.com.lucasatolini.attornatus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity(name = "user")
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, length = 100)
    @NotNull(message = "Name must not be blank")
    @Max(message = "Name should not exceed 100 characters, please use abbreviations", value = 100)
    private String nome;

    @Column(nullable = false, updatable = false)
    @NotNull(message = "Birth date must not be blank")
    private Date dataNascimento;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Adress> listEnd;

    @OneToOne
    @NotNull(message = "Adress must not be blank")
    private Adress principalEnd;

    public User() {}

    public User(String nome, Date dataNascimento, Adress principalEnd) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.principalEnd = principalEnd;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Adress> getListEnd() {
        return listEnd;
    }

    public void setListEnd(List<Adress> listEnd) {
        this.listEnd = listEnd;
    }

    public Adress getPrincipalEnd() {
        return principalEnd;
    }

    public void setPrincipalEnd(Adress principalEnd) {
        this.principalEnd = principalEnd;
    }
}
