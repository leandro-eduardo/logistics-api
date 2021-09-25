package com.springboot.logistics.domain.repository;

import java.util.List;
import java.util.Optional;

import com.springboot.logistics.domain.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // anotação que define que a interface (ClienteRepository neste caso) é um
			// componente do Spring porém com uma semântica muito bem definida, ou seja, é
			// um repositório. Um repositório é o que gerencia uma entidade, neste caso é a
			// entidade Cliente
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByName(String name);

	List<Customer> findByNameContaining(String name);

	Optional<Customer> findByEmail(String email);

}