package br.com.lucasatolini.attornatus.repository;

import br.com.lucasatolini.attornatus.model.Address;
import br.com.lucasatolini.attornatus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("update User u set u.name = ?1, u.birthDate = ?2 where u.id = ?3")
    int update(String name, Date birthDate, Long id);
}
