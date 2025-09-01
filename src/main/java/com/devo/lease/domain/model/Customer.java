package com.devo.lease.domain.model;

import com.devo.lease.domain.model.value.CustomerId;

public final class Customer {
    private final CustomerId id;
    private final String name;
    public Customer(CustomerId id, String name) {
        this.id = id;
        this.name = name;
    }
    public CustomerId id() { return id; }
}
