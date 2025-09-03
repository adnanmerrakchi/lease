package com.devo.lease.domain.model;

import com.devo.lease.domain.model.value.CarId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private static CarId newCarId() {
        return new CarId(UUID.randomUUID());
    }


    @Test
    @DisplayName("[SUCCESS] creates a Car when VIN is not null")
    void createsCarOnValidVin() {
        var id = newCarId();
        var car = new Car(id, "55555A44", "Volkswagen", "T-Roc", 2022, 450.0);

        assertAll(
                () -> assertNotNull(car),
                () -> assertEquals(id, car.id()),
                () -> assertEquals(450.0, car.rentPrice())
        );
    }

    @Test
    @DisplayName("[FAILURE] throws when VIN is null")
    void throwsOnNullVin() {
        assertThrows(IllegalArgumentException.class, () ->
                new Car(newCarId(), null, "Ford", "Fiesta", 2019, 350.0)
        );
    }

}