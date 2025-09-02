package com.devo.lease.domain.model;

import com.devo.lease.domain.model.value.CarId;
import com.devo.lease.domain.model.value.CustomerId;
import com.devo.lease.domain.model.value.LeaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class LeaseTest {
    
    private static LeaseId newLeaseId() {
        return new LeaseId(UUID.randomUUID());
    }
    
    private static CarId newCarId() {
        return new CarId(UUID.randomUUID());
    }
    
    private static CustomerId newCustomerId() {
        return new CustomerId(UUID.randomUUID());
    }

    @Nested
    class StartLease {

        @Test
        @DisplayName("[SUCCESS] Start lease")
        void startLease_success() {
            var id = newLeaseId();
            var carId = newCarId();
            var customerId = newCustomerId();
            var start = LocalDateTime.parse("2025-09-02T09:00:00");
            var expected = LocalDateTime.parse("2025-09-04T09:00:00");

            var lease = Lease.startLease(id, carId, customerId, start, expected);

            assertAll(
                    () -> assertEquals(id, lease.id()),
                    () -> assertEquals(carId, lease.carId()),
                    () -> assertEquals(customerId, lease.customerId()),
                    () -> assertEquals(start, lease.startAt()),
                    () -> assertEquals(expected, lease.expectedReturnAt()),
                    () -> assertEquals(Lease.Status.ACTIVE, lease.status()),
                    () -> assertNull(lease.returnedAt())
            );
        }

        @Test
        @DisplayName("[FAILURE] Start lease with invalid start date")
        void startLease_invalidStart() {
            var id = newLeaseId();
            var carId = newCarId();
            var customerId = newCustomerId();
            var start = LocalDateTime.parse("2025-09-02T09:00:00");
            var expected = LocalDateTime.parse("2025-09-01T09:00:00");

            assertThrows(   IllegalArgumentException.class, () ->
                            Lease.startLease(id, carId, customerId, start, expected));
        }

        @Test
        @DisplayName("[FAILURE] start date equals expected date")
        void open_throws_when_equal_times() {
            var id = newLeaseId();
            var carId = newCarId();
            var customerId = newCustomerId();
            var start = LocalDateTime.parse("2025-09-02T09:00:00");
            var expected = LocalDateTime.parse("2025-09-02T09:00:00");
            assertThrows(IllegalArgumentException.class, () ->
                    Lease.startLease(id, carId, customerId, start, expected));
        }

    }

    @Nested
    class CloseLease {

        @Test
        @DisplayName("[SUCCESS] Close lease")
        void closeLease_success() {
            var id = newLeaseId();
            var carId = newCarId();
            var customerId = newCustomerId();
            var start = LocalDateTime.parse("2025-09-02T09:00:00");
            var expected = LocalDateTime.parse("2025-09-04T09:00:00");
            var actualReturnAt = LocalDateTime.parse("2025-09-03T09:00:00");

            var lease = Lease.startLease(id, carId, customerId, start, expected);
            lease.closeLease(actualReturnAt);

            assertAll(
                    () -> assertEquals(id, lease.id()),
                    () -> assertEquals(carId, lease.carId()),
                    () -> assertEquals(customerId, lease.customerId()),
                    () -> assertEquals(start, lease.startAt()),
                    () -> assertEquals(expected, lease.expectedReturnAt()),
                    () -> assertEquals(Lease.Status.CLOSED, lease.status())
            );
        }

        @Test
        @DisplayName("[FAILURE] Close lease with invalid return date")
        void closeLease_invalidReturn() {
            var id = newLeaseId();
            var carId = newCarId();
            var customerId = newCustomerId();
            var start = LocalDateTime.parse("2025-09-02T09:00:00");
            var expected = LocalDateTime.parse("2025-09-04T09:00:00");
            var actualReturnAt = LocalDateTime.parse("2025-09-01T09:00:00");

            var lease = Lease.startLease(id, carId, customerId, start, expected);
            assertThrows(IllegalArgumentException.class, () ->
                    lease.closeLease(actualReturnAt));
        }

        @Test
        @DisplayName("[FAILURE] Close a closed lease !!!")
        void closeLease_closeAClosedLease() {
            var id = newLeaseId();
            var carId = newCarId();
            var customerId = newCustomerId();
            var start = LocalDateTime.parse("2025-09-02T09:00:00");
            var expected = LocalDateTime.parse("2025-09-04T09:00:00");
            var actualReturnAt = LocalDateTime.parse("2025-09-03T09:00:00");

            var lease = Lease.startLease(id, carId, customerId, start, expected);
            lease.closeLease(actualReturnAt);

            assertThrows(IllegalStateException.class, () ->
                    lease.closeLease(actualReturnAt));
        }
    }

  
}