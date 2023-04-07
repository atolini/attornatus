package br.com.lucasatolini.attornatus.repository;

import br.com.lucasatolini.attornatus.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
