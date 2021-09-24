package com.springboot.logistics.domain.service;

import com.springboot.logistics.domain.model.Cliente;
import com.springboot.logistics.domain.repository.ClienteRepository;
import com.springboot.logistics.domain.service.exceptions.ClienteExistenteException;
import com.springboot.logistics.domain.service.exceptions.ClienteNaoEncontradoException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service // anotação que torna a classe um componente Spring com a semântica de um
			// serviço, representando os serviços que vão ser executados, onde são inseridas
			// as regras de negócio
public class ClienteService {

	private ClienteRepository clienteRepository;

	public Page<Cliente> listar(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	public Cliente buscar(Long clienteId) {
		return clienteRepository.findById(clienteId).orElseThrow(
				() -> new ClienteNaoEncontradoException("Cliente com o ID " + clienteId + " não encontrado"));
	}

	@Transactional // declara que este método deve ser executado dentro de uma transação, ou seja,
					// se algo que estiver executando dentro deste método der errado, todas as
					// operações dentro dessa transação que estão sendo feitas no banco de dados são
					// descartadas (ou tudo é executado ou nada é executado quando persistimos dados
					// no banco)
	public Cliente incluir(Cliente cliente) {
		boolean emailExistente = clienteRepository.findByEmail(cliente.getEmail()).stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
		if (emailExistente) {
			throw new ClienteExistenteException("Já existe um cliente cadastrado com este e-mail");
		}
		return clienteRepository.save(cliente);
	}

	@Transactional
	public Cliente atualizar(Long clienteId, Cliente cliente) {
		Cliente clienteSalvo = buscar(clienteId);
		BeanUtils.copyProperties(cliente, clienteSalvo);
		clienteSalvo.setId(clienteId);
		return clienteRepository.save(clienteSalvo);
	}

	@Transactional
	public void excluir(Long clienteId) {
		Cliente clienteSalvo = buscar(clienteId);
		clienteRepository.deleteById(clienteSalvo.getId());
	}

}