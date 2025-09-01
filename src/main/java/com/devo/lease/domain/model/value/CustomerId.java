package com.devo.lease.domain.model.value;

import java.util.UUID;

public record CustomerId(UUID value) {
    public CustomerId {
        if (value == null) throw new IllegalArgumentException("CustomerId null");
    }
}