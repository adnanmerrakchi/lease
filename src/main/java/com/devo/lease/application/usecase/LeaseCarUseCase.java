package com.devo.lease.application.usecase;

import com.devo.lease.application.command.LeaseCarCommand;
import com.devo.lease.application.dto.LeaseReceiptDTO;
import com.devo.lease.domain.model.Lease;
import com.devo.lease.domain.model.value.CarId;
import com.devo.lease.domain.model.value.CustomerId;
import com.devo.lease.domain.model.value.LeaseId;
import com.devo.lease.domain.ports.CarRepository;
import com.devo.lease.domain.ports.CustomerRepository;
import com.devo.lease.domain.ports.LeaseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LeaseCarUseCase {

    private final CarRepository carRepo;
    private final CustomerRepository customerRepo;
    private final LeaseRepository leaseRepo;

    public LeaseCarUseCase(CarRepository carRepo,
                           CustomerRepository customerRepo,
                           LeaseRepository leaseRepo) {
        this.carRepo = carRepo;
        this.customerRepo = customerRepo;
        this.leaseRepo = leaseRepo;
    }

    @Transactional
    public LeaseReceiptDTO leaseCar(LeaseCarCommand leaseCarCommand) {
        var carId = new CarId(leaseCarCommand.carId());
        var customerId = new CustomerId(leaseCarCommand.customerId());
        var car = carRepo.findById(carId).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        customerRepo.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        var lease = Lease.startLease( new LeaseId(UUID.randomUUID()),
                                carId,
                                customerId,
                                leaseCarCommand.startAt(),
                                leaseCarCommand.expectedReturnAt());

        lease = leaseRepo.save(lease);

        return new LeaseReceiptDTO(lease.id().value(), car.id().value(), customerId.value(), lease.startAt(), lease.expectedReturnAt());
    }
}
