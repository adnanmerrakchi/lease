package com.devo.lease.adapter.web;


import com.devo.lease.adapter.web.request.LeaseCarRequest;
import com.devo.lease.adapter.web.request.ReturnCarRequest;
import com.devo.lease.adapter.web.response.LeaseResponse;
import com.devo.lease.adapter.web.response.ReturnResponse;
import com.devo.lease.application.command.LeaseCarCommand;
import com.devo.lease.application.command.ReturnCarCommand;
import com.devo.lease.application.dto.LeaseReceiptDTO;
import com.devo.lease.application.dto.ReturnReceiptDTO;
import com.devo.lease.application.usecase.LeaseCarUseCase;
import com.devo.lease.application.usecase.ReturnCarUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Leases", description = "Lease a car and return it")
@RestController
@RequestMapping("/api/v1/leases")
public class LeaseController {

    private final LeaseCarUseCase leaseCarUseCase;
    private final ReturnCarUseCase returnCarUseCase;

    public LeaseController(LeaseCarUseCase leaseCarUseCase, ReturnCarUseCase returnCarUseCase) {
        this.leaseCarUseCase = leaseCarUseCase;
        this.returnCarUseCase = returnCarUseCase;
    }


    @Operation(
            summary = "Lease a car",
            description = "Creates a new lease for the given car and customer between startAt and expectedReturnAt."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lease created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeaseReceiptDTO.class),
                            examples = @ExampleObject(name = "LeaseReceiptDTO", value = """
        {
          "leaseId":"5a9f6f61-6c34-4eb5-ae1b-2f2a6a6dba50",
          "carId":"00000000-0000-0000-0000-000000000101",
          "customerId":"00000000-0000-0000-0000-00000000A001",
          "startAt":"2025-09-02T09:00:00",
          "expectedReturnAt":"2025-09-04T09:00:00"
        }"""))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Car or customer not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Car not available (overlapping lease)", content = @Content)
    })
    @PostMapping
    public ResponseEntity<LeaseResponse> lease(@RequestBody @Valid LeaseCarRequest req) {
        var dto = leaseCarUseCase.leaseCar(new LeaseCarCommand(   req.carId(),
                                                                req.customerId(),
                                                                req.startAt(),
                                                                req.expectedReturnAt()));
        return ResponseEntity.ok(new LeaseResponse( dto.leaseId(),
                                                    dto.carId(),
                                                    dto.customerId(),
                                                    dto.startAt(),
                                                    dto.expectedReturnAt()));
    }

    @Operation(
            summary = "Return a leased car",
            description = "Closes an active lease and returns the car."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lease closed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReturnReceiptDTO.class),
                            examples = @ExampleObject(name = "ReturnReceiptDTO", value = """
        {
          "leaseId":"5a9f6f61-6c34-4eb5-ae1b-2f2a6a6dba50",
          "carId":"00000000-0000-0000-0000-000000000101",
          "customerId":"00000000-0000-0000-0000-00000000A001",
          "returnedAt":"2025-09-04T10:00:00",
          "rentPrice":35.0
        }"""))),
            @ApiResponse(responseCode = "400", description = "Invalid returnedAt", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lease or car not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Lease already closed", content = @Content)
    })
    @PostMapping("/{leaseId}/return")
    public ResponseEntity<ReturnResponse> returnCar(    @PathVariable UUID leaseId,
                                                        @RequestBody @Valid ReturnCarRequest req) {
        var dto = returnCarUseCase.returnCar(new ReturnCarCommand(leaseId, req.returnedAt()));
        return ResponseEntity.ok(   new ReturnResponse( dto.leaseId(),
                                                        dto.carId(),
                                                        dto.customerId(),
                                                        dto.returnedAt(),
                                                        dto.rentPrice()));
    }
}
