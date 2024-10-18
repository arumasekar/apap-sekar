package apap.ti.hospitalization2206816115.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.ti.hospitalization2206816115.model.Facility;
import apap.ti.hospitalization2206816115.model.Reservation;

@Repository
public interface ReservationDb extends JpaRepository<Reservation, String> {
    
}
