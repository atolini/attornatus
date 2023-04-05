package br.com.lucasatolini.attornatus.repository;

import br.com.lucasatolini.attornatus.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
