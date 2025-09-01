package com.devo.lease.domain.model.value;

import java.util.UUID;

public record LeaseId(UUID value) {
    public LeaseId {
        if (value == null) throw new IllegalArgumentException("LeaseId null");
    }
}