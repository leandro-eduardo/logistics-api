package com.springboot.logistics.api.controller;

import java.net.URI;

import javax.validation.Valid;

import com.springboot.logistics.domain.model.Customer;
import com.springboot.logistics.domain.service.CustomerService;

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
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService customerService;

	@GetMapping
	public Page<Customer> list(Pageable pageable) {
		return customerService.list(pageable);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> find(@PathVariable Long customerId) {
		return ResponseEntity.ok(customerService.find(customerId));
	}

	@PostMapping
	public ResponseEntity<Customer> insert(@Valid @RequestBody Customer customer) {
		customerService.insert(customer);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerId}")
				.buildAndExpand(customer.getId()).toUri();
		return ResponseEntity.created(uri).body(customer);
	}

	@PutMapping("/{customerId}")
	public ResponseEntity<Customer> update(@Valid @PathVariable Long customerId, @RequestBody Customer customer) {
		Customer savedCustomer = customerService.update(customerId, customer);
		return ResponseEntity.ok(savedCustomer);
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<Void> delete(@PathVariable Long customerId) {
		customerService.delete(customerId);
		return ResponseEntity.noContent().build();
	}

}