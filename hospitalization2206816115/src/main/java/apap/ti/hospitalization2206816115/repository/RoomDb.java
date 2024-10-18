package apap.ti.hospitalization2206816115.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import apap.ti.hospitalization2206816115.model.Facility;
import apap.ti.hospitalization2206816115.model.Reservation;
import apap.ti.hospitalization2206816115.model.Room;

@Repository
public interface RoomDb extends JpaRepository<Room, String> {
    List<Room> findAllByIsDeletedFalse();
    Optional<Room> findByIdAndIsDeletedFalse(String id);
    @Query("SELECT r FROM Room r WHERE r.id = :roomId AND r.isDeleted = false")
    Optional<Room> findDetailRoomById(String roomId);
    // Query untuk mendapatkan ruangan dengan reservasi pada rentang waktu tertentu
    @Query("SELECT r FROM Room r LEFT JOIN r.reservations res " +
           "WHERE r.id = :roomId AND r.isDeleted = false " +
           "AND (res IS NULL OR " +
           "(NOT (:dateIn BETWEEN res.dateIn AND res.dateOut) AND " +
           "NOT (:dateOut BETWEEN res.dateIn AND res.dateOut)))")
    Optional<Room> findRoomWithAvailability(String roomId, LocalDate dateIn, LocalDate dateOut);
    
}
