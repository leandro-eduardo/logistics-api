package com.springboot.logistics.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.logistics.domain.exception.Exception;
import com.springboot.logistics.domain.model.Cliente;
import com.springboot.logistics.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service // anotação que torna a classe um componente Spring com a semântica de um
			// serviço, representando os serviços que vão ser executados, onde são inseridas
			// as regras de negócio
public class ClienteService {

	private ClienteRepository clienteRepository;

	@Transactional // declara que este método deve ser executado dentro de uma transação, ou seja,
					// se algo que estiver executando dentro deste método der errado, todas as
					// operações dentro dessa transação que estão sendo feitas no banco de dados são
					// descartadas (ou tudo é executado ou nada é executado quando persistimos dados
					// no banco)
	public Cliente salvar(Cliente cliente) {
		boolean emailExistente = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
		
		if (emailExistente) {
			throw new Exception("Já existe um cliente cadastrado com este e-mail.");
		}
		
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void excluir(Long id) {
		clienteRepository.deleteById(id);
	}

}