package apap.ti.hospitalization2206816115.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.ti.hospitalization2206816115.model.Facility;

@Repository
public interface FacilityDb extends JpaRepository<Facility, UUID> {
    List<Facility> findByIsDeletedFalse();
}
