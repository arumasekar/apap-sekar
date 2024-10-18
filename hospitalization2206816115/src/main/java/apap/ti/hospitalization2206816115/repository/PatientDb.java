package apap.ti.hospitalization2206816115.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.ti.hospitalization2206816115.model.Facility;
import apap.ti.hospitalization2206816115.model.Patient;

@Repository
public interface PatientDb extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByNik(String nik);
    boolean existsByNik(String nik);
    boolean existsByEmail(String email);
}
