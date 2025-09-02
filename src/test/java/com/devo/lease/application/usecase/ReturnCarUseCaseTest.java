package com.devo.lease.application.usecase;

import com.devo.lease.application.command.ReturnCarCommand;
import com.devo.lease.application.dto.ReturnReceiptDTO;
import com.devo.lease.domain.model.Car;
import com.devo.lease.domain.model.value.CarId;
import com.devo.lease.domain.model.value.CustomerId;
import com.devo.lease.domain.model.value.LeaseId;
import com.devo.lease.domain.ports.CarRepository;
import com.devo.lease.domain.ports.LeaseRepository;
import com.devo.lease.domain.model.Lease;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReturnCarUseCaseTest {

    @Mock
    LeaseRepository leaseRepo;
    @Mock
    CarRepository carRepo;

    @InjectMocks
    ReturnCarUseCase useCase;

    @Test
    @DisplayName("[SUCCESS] happy path to return a leased car")
    void returnCar_success() {

        var leaseId = new LeaseId(UUID.randomUUID());
        var carId = new CarId(UUID.randomUUID());
        var customerId = new CustomerId(UUID.randomUUID());
        var start = LocalDateTime.parse("2025-09-02T09:00:00");
        var expected = LocalDateTime.parse("2025-09-04T09:00:00");
        var lease = Lease.startLease(leaseId, carId, customerId, start, expected);

        when(leaseRepo.findById(any(LeaseId.class))).thenReturn(Optional.of(lease));

        var car = new Car(carId, "55555A44", "Ford", "Fiesta", 2022, 350.0);
        when(carRepo.findById(carId)).thenReturn(Optional.of(car));

        when(leaseRepo.save(any(Lease.class))).thenAnswer(inv -> inv.getArgument(0));

        var returnedAt = LocalDateTime.parse("2025-09-04T10:00:00");
        var cmd = new ReturnCarCommand(leaseId.value(), returnedAt);

        ReturnReceiptDTO out = useCase.returnCar(cmd);

        assertNotNull(out);
        assertEquals(leaseId.value(), out.leaseId());
        assertEquals(customerId.value(), out.customerId());
        assertEquals(returnedAt, out.returnedAt());
        assertEquals(350.0, out.rentPrice());

    }

    @Test
    @DisplayName("[FAILURE] throws when lease not found")
    void returnCar_leaseNotFound_throws() {
        when(leaseRepo.findById(any())).thenReturn(Optional.empty());

        var cmd = new ReturnCarCommand(UUID.randomUUID(), LocalDateTime.parse("2025-09-03T10:00:00"));

        assertThrows(IllegalArgumentException.class, () -> useCase.returnCar(cmd));
    }

    @Test
    @DisplayName("[FAILURE] throws when car not found")
    void returnCar_carNotFound_throws() {
        var leaseId = new LeaseId(UUID.randomUUID());
        when(leaseRepo.findById(any())).thenReturn( Optional.of(    Lease.startLease(leaseId,
                                                                    new CarId(UUID.randomUUID()),
                                                                    new CustomerId(UUID.randomUUID()),
                                                                    LocalDateTime.parse("2025-09-02T09:00:00"),
                                                                    LocalDateTime.parse("2025-09-04T09:00:00"))));
        when(carRepo.findById(any())).thenReturn(Optional.empty());

        var cmd = new ReturnCarCommand(leaseId.value(), LocalDateTime.parse("2025-09-02T10:00:00"));

        assertThrows(IllegalStateException.class, () -> useCase.returnCar(cmd));

    }

}