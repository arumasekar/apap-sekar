package apap.ti.hospitalization2206816115.repository;

import apap.ti.hospitalization2206816115.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NurseDb extends JpaRepository<Nurse, UUID> {
    
    // Find nurse by id where not soft deleted
    Optional<Nurse> findByIdAndIsDeletedFalse(UUID id);
    
    // Find all nurses that are not soft deleted
    List<Nurse> findAllByIsDeletedFalse();
    
    // Check if email exists (for validation)
    boolean existsByEmailAndIsDeletedFalse(String email);
    
    // Find by email where not soft deleted
    Optional<Nurse> findByEmailAndIsDeletedFalse(String email);
    
    // Custom query to find nurses with their reservation count
    @Query("""
        SELECT n, COUNT(r) 
        FROM Nurse n 
        LEFT JOIN Reservation r ON r.nurse = n AND r.isDeleted = false 
        WHERE n.isDeleted = false 
        GROUP BY n
    """)
    List<Object[]> findAllNursesWithReservationCount();
}