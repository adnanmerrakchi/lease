package com.devo.lease.domain.model;

import com.devo.lease.domain.model.value.CarId;
import com.devo.lease.domain.model.value.LeaseId;
import com.devo.lease.domain.model.value.CustomerId;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Lease {
    public enum Status { ACTIVE, CLOSED }

    private final LeaseId id;
    private final CarId carId;
    private final CustomerId customerId;
    private final LocalDateTime startAt;
    private final LocalDateTime expectedReturnAt;
    private LocalDateTime returnedAt;
    private Status status;

    private Lease(LeaseId id, CarId carId, CustomerId customerId,
                  LocalDateTime startAt, LocalDateTime expectedReturnAt) {
        this.id = Objects.requireNonNull(id);
        this.carId = Objects.requireNonNull(carId);
        this.customerId = Objects.requireNonNull(customerId);
        this.startAt = Objects.requireNonNull(startAt);
        this.expectedReturnAt = Objects.requireNonNull(expectedReturnAt);
        this.status = Status.ACTIVE;
    }

    public static Lease startLease(LeaseId id, CarId carId, CustomerId customerId,
                                   LocalDateTime startAt, LocalDateTime expectedReturnAt) {
        if (!startAt.isBefore(expectedReturnAt))
            throw new IllegalArgumentException("start must be before expected return");
        return new Lease(id, carId, customerId, startAt, expectedReturnAt);
    }

    public void closeLease(LocalDateTime actualReturnAt) {
        if (status == Status.CLOSED) throw new IllegalStateException("Already closed");
        if (actualReturnAt.isBefore(startAt)) throw new IllegalArgumentException("return before start");
        this.returnedAt = actualReturnAt;
        this.status = Status.CLOSED;
    }

    public LeaseId id() { return id; }
    public CarId carId() { return carId; }
    public CustomerId customerId() { return customerId; }
    public LocalDateTime startAt() { return startAt; }
    public LocalDateTime expectedReturnAt() { return expectedReturnAt; }
    public LocalDateTime returnedAt() { return returnedAt; }
    public Status status() { return status; }
}
