package com.devo.lease.application.usecase;

import com.devo.lease.application.command.LeaseCarCommand;
import com.devo.lease.application.dto.LeaseReceiptDTO;
import com.devo.lease.domain.model.Car;
import com.devo.lease.domain.model.Customer;
import com.devo.lease.domain.model.Lease;
import com.devo.lease.domain.model.value.CarId;
import com.devo.lease.domain.model.value.CustomerId;
import com.devo.lease.domain.ports.CarRepository;
import com.devo.lease.domain.ports.CustomerRepository;
import com.devo.lease.domain.ports.LeaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class LeaseCarUseCaseTest {

    @Mock
    CarRepository carRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    LeaseRepository leaseRepository;

    @InjectMocks LeaseCarUseCase useCase;

    @Test
    @DisplayName("[SUCCESS] happy path of leasing a car use case")
    void leaseCar_success() {

        UUID carUuid = UUID.fromString("00000000-0000-0000-0000-000000000101");
        UUID customerUuid = UUID.fromString("00000000-0000-0000-0000-00000000A001");
        var carId = new CarId(carUuid);
        var customerId = new CustomerId(customerUuid);

        var car = new Car(carId, "VIN123456789012345", "Ford", "Fiesta", 2022, 250.0);
        var customer = new Customer(customerId, "Adnan");

        when(carRepository.findById(eq(carId))).thenReturn(Optional.of(car));
        when(customerRepository.findById(eq(customerId))).thenReturn(Optional.of(customer));

        when(leaseRepository.save(any(Lease.class))).thenAnswer(inv -> inv.getArgument(0));

        var startAt = LocalDateTime.parse("2025-09-02T09:00:00");
        var expectedReturnAt = LocalDateTime.parse("2025-09-04T09:00:00");
        var cmd = new LeaseCarCommand(carUuid, customerUuid, startAt, expectedReturnAt);

        LeaseReceiptDTO out = useCase.leaseCar(cmd);

        assertNotNull(out);
        assertNotNull(out.leaseId());
        assertEquals(carUuid, out.carId());
        assertEquals(customerUuid, out.customerId());
        assertEquals(startAt, out.startAt());
        assertEquals(expectedReturnAt, out.expectedReturnAt());
    }

    @Test
    @DisplayName("[FAILURE] customer not found")
    void leaseCar_customerNotFound() {

        UUID carUuid = UUID.randomUUID();
        UUID customerUuid = UUID.randomUUID();
        var carId = new CarId(carUuid);
        var customerId = new CustomerId(customerUuid);

        var car = new Car(carId, "555555A44", "VW", "T-Roc", 2022, 450.0);
        when(carRepository.findById(eq(carId))).thenReturn(Optional.of(car));
        when(customerRepository.findById(eq(customerId))).thenReturn(Optional.empty());

        var cmd = new LeaseCarCommand(  carUuid,
                                        customerUuid,
                                        LocalDateTime.parse("2025-09-02T09:00:00"),
                                        LocalDateTime.parse("2025-09-04T09:00:00"));

        assertThrows(IllegalArgumentException.class, () -> useCase.leaseCar(cmd));

    }

    @Test
    @DisplayName("[FAILURE] car not found")
    void leaseCar_carNotFound() {

        UUID carUuid = UUID.randomUUID();
        UUID customerUuid = UUID.randomUUID();

        when(carRepository.findById(any())).thenReturn(Optional.empty());

        var cmd = new LeaseCarCommand(  carUuid,
                                        customerUuid,
                                        LocalDateTime.parse("2025-09-02T09:00:00"),
                                        LocalDateTime.parse("2025-09-04T09:00:00"));

        assertThrows(IllegalArgumentException.class, () -> useCase.leaseCar(cmd));
    }
}