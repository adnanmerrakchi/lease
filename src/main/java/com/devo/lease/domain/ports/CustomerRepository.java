package com.devo.lease.domain.ports;

import com.devo.lease.domain.model.Customer;
import com.devo.lease.domain.model.value.CustomerId;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findById(CustomerId id);

}
