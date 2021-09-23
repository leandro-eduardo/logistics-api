package com.springboot.logistics.domain.repository;

import java.util.List;
import java.util.Optional;

import com.springboot.logistics.domain.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // anotação que define que a interface (ClienteRepository neste caso) é um
			// componente do Spring porém com uma semântica muito bem definida, ou seja, é
			// um repositório. Um repositório é o que gerencia uma entidade, neste caso é a entidade Cliente
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByNome(String nome);
	List<Cliente> findByNomeContaining(String nome);
	Optional<Cliente> findByEmail(String email);

}