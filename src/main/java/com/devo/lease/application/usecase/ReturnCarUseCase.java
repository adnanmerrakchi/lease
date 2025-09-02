package com.devo.lease.application.usecase;

import com.devo.lease.application.command.ReturnCarCommand;
import com.devo.lease.application.dto.ReturnReceiptDTO;
import com.devo.lease.domain.model.Lease;
import com.devo.lease.domain.model.value.LeaseId;
import com.devo.lease.domain.ports.CarRepository;
import com.devo.lease.domain.ports.LeaseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReturnCarUseCase {

    private final LeaseRepository leaseRepo;
    private final CarRepository carRepo;

    public ReturnCarUseCase(    LeaseRepository leaseRepo,
                                CarRepository carRepo) {
        this.leaseRepo = leaseRepo;
        this.carRepo = carRepo;
    }

    @Transactional
    public ReturnReceiptDTO returnCar(ReturnCarCommand returnCarCommand) {
        var lease = leaseRepo.findById(new LeaseId(returnCarCommand.leaseId()))
                            .orElseThrow(() -> new IllegalArgumentException("Lease not found"));
        if (lease.status() == Lease.Status.CLOSED)
            throw new IllegalStateException("Lease already closed");

        var car = carRepo.findById(lease.carId()).orElseThrow(() -> new IllegalStateException("Car missing"));

        lease.closeLease(returnCarCommand.returnedAt());
        leaseRepo.save(lease);

        return new ReturnReceiptDTO(lease.id().value(), lease.carId().value(), lease.customerId().value(), returnCarCommand.returnedAt(), car.rentPrice());

    }
}
