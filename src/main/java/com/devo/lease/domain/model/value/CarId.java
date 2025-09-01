package com.devo.lease.domain.model.value;

import java.util.UUID;

public record CarId(UUID value) {
    public CarId {
        if (value == null) throw new IllegalArgumentException("CarId null");
    }
}
