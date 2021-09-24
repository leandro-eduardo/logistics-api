package com.springboot.logistics.api.controller;

import java.net.URI;

import javax.validation.Valid;

import com.springboot.logistics.domain.model.Cliente;
import com.springboot.logistics.domain.service.ClienteService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private ClienteService clienteService;

	@GetMapping
	public Page<Cliente> listar(Pageable pageable) {
		return clienteService.listar(pageable);
	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		return ResponseEntity.ok(clienteService.buscar(clienteId));
	}

	@PostMapping
	public ResponseEntity<Cliente> incluir(@Valid @RequestBody Cliente cliente) {
		clienteService.incluir(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{clienteId}").buildAndExpand(cliente.getId())
				.toUri();
		return ResponseEntity.created(uri).body(cliente);
	}

	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) {
		Cliente clienteSalvo = clienteService.atualizar(clienteId, cliente);
		return ResponseEntity.ok(clienteSalvo);
	}

	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> excluir(@PathVariable Long clienteId) {
		clienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}

}