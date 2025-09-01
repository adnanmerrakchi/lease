package com.devo.lease.adapter.web;


import com.devo.lease.adapter.web.request.LeaseCarRequest;
import com.devo.lease.adapter.web.request.ReturnCarRequest;
import com.devo.lease.adapter.web.response.LeaseResponse;
import com.devo.lease.adapter.web.response.ReturnResponse;
import com.devo.lease.application.command.LeaseCarCommand;
import com.devo.lease.application.command.ReturnCarCommand;
import com.devo.lease.application.usecase.LeaseCarUseCase;
import com.devo.lease.application.usecase.ReturnCarUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api/v1/leases")
public class LeaseController {

    private final LeaseCarUseCase leaseCarUseCase;
    private final ReturnCarUseCase returnCarUseCase;

    public LeaseController(LeaseCarUseCase leaseCarUseCase, ReturnCarUseCase returnCarUseCase) {
        this.leaseCarUseCase = leaseCarUseCase;
        this.returnCarUseCase = returnCarUseCase;
    }

    @PostMapping
    public ResponseEntity<LeaseResponse> lease(@RequestBody @Valid LeaseCarRequest req) {
        var dto = leaseCarUseCase.handle(new LeaseCarCommand(   req.carId(),
                                                                req.customerId(),
                                                                req.startAt(),
                                                                req.expectedReturnAt()));
        return ResponseEntity.ok(new LeaseResponse( dto.leaseId(),
                                                    dto.carId(),
                                                    dto.customerId(),
                                                    dto.startAt(),
                                                    dto.expectedReturnAt()));
    }

    @PostMapping("/{leaseId}/return")
    public ResponseEntity<ReturnResponse> returnCar(    @PathVariable UUID leaseId,
                                                        @RequestBody @Valid ReturnCarRequest req) {
        var dto = returnCarUseCase.handle(new ReturnCarCommand(leaseId, req.returnedAt()));
        return ResponseEntity.ok(   new ReturnResponse( dto.leaseId(),
                                                        dto.carId(),
                                                        dto.customerId(),
                                                        dto.returnedAt(),
                                                        dto.rentPrice()));
    }
}
