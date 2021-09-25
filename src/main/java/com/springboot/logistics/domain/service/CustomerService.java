package com.springboot.logistics.domain.service;

import com.springboot.logistics.domain.model.Customer;
import com.springboot.logistics.domain.repository.CustomerRepository;
import com.springboot.logistics.domain.service.exceptions.CustomerNotFoundException;
import com.springboot.logistics.domain.service.exceptions.RegisteredCustomerException;

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
public class CustomerService {

	private CustomerRepository customerRepository;

	public Page<Customer> list(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	public Customer find(Long customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("customer with ID " + customerId + " not found"));
	}

	@Transactional // declara que este método deve ser executado dentro de uma transação, ou seja,
					// se algo que estiver executando dentro deste método der errado, todas as
					// operações dentro dessa transação que estão sendo feitas no banco de dados são
					// descartadas (ou tudo é executado ou nada é executado quando persistimos dados
					// no banco)
	public void insert(Customer customer) {
		boolean isCustomerRegistered = customerRepository.findByEmail(customer.getEmail()).isPresent();
		if (isCustomerRegistered) {
			throw new RegisteredCustomerException("there is already a customer registered with this email");
		}
		customerRepository.save(customer);
	}

	@Transactional
	public Customer update(Long customerId, Customer customer) {
		Customer savedCustomer = find(customerId);
		BeanUtils.copyProperties(customer, savedCustomer);
		savedCustomer.setId(customerId);
		return customerRepository.save(savedCustomer);
	}

	@Transactional
	public void delete(Long customerId) {
		Customer savedCustomer = find(customerId);
		customerRepository.deleteById(savedCustomer.getId());
	}

}