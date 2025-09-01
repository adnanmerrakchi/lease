package com.devo.lease.adapter.persistence.impl;

import com.devo.lease.adapter.persistence.mapper.JpaMappers;
import com.devo.lease.adapter.persistence.repository.CustomerJpaRepository;
import com.devo.lease.domain.model.Customer;
import com.devo.lease.domain.model.value.CustomerId;
import com.devo.lease.domain.ports.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerRepo;

    public CustomerRepositoryAdapter(CustomerJpaRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        return customerRepo.findById(id.value()).map(e-> new Customer(id, e.getName()));
    }
}
