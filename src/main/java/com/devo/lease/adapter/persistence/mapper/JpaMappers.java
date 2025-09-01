package com.devo.lease.adapter.persistence.mapper;

import com.devo.lease.adapter.persistence.entity.CarEntity;
import com.devo.lease.adapter.persistence.entity.LeaseEntity;
import com.devo.lease.domain.model.Car;
import com.devo.lease.domain.model.Lease;
import com.devo.lease.domain.model.value.CarId;
import com.devo.lease.domain.model.value.CustomerId;
import com.devo.lease.domain.model.value.LeaseId;

public final class JpaMappers {

    private JpaMappers(){}

    public static Car toDomain(CarEntity e) {
        return new Car(new  CarId(e.getId()),
                            e.getCrn(),
                            e.getBrand(),
                            e.getModel(),
                            e.getReleaseYear(),
                            e.getRentPrice());
    }

    public static Lease toDomain(LeaseEntity e) {
        var lease = Lease.open( new LeaseId( e.getId()),
                                new CarId(e.getCarId()),
                                new CustomerId(e.getCustomerId()),
                                e.getStartAt(),
                                e.getExpectedReturnAt());
        if ("CLOSED".equals(e.getStatus()) && e.getReturnedAt()!=null) {
            lease.close(e.getReturnedAt());
        }
        return lease;
    }

    public static LeaseEntity toEntity(Lease d) {
        var e = new LeaseEntity();
        e.setId(d.id().value());
        e.setCarId(d.carId().value());
        e.setCustomerId(d.customerId().value());
        e.setStartAt(d.startAt());
        e.setExpectedReturnAt(d.expectedReturnAt());
        e.setReturnedAt(d.returnedAt());
        e.setStatus(d.status().name());
        return e;
    }

}
