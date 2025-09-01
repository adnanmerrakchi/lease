package com.devo.lease.domain.model.events;


import java.time.LocalDateTime;

import com.devo.lease.domain.model.value.CustomerId;
import com.devo.lease.domain.model.value.CarId;
import com.devo.lease.domain.model.value.LeaseId;

public record CarReturned(LeaseId leaseId, CarId carId, CustomerId customerId, LocalDateTime at) {
}