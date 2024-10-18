package apap.ti.hospitalization2206816115.service;

import apap.ti.hospitalization2206816115.dto.request.AddReservationRequestDTO;
import apap.ti.hospitalization2206816115.model.Reservation;
import apap.ti.hospitalization2206816115.model.Room;
import apap.ti.hospitalization2206816115.model.Facility;
import apap.ti.hospitalization2206816115.model.Nurse;
import apap.ti.hospitalization2206816115.repository.ReservationDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDb reservationDb;

    @Autowired
    private RoomService roomService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private FacilityService facilityService;

    @Override
    public Reservation createReservationFromDTO(AddReservationRequestDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setDateIn(dto.getDateIn());
        reservation.setDateOut(dto.getDateOut());

        Room room = roomService.getRoomById(dto.getRoomId().toString());
        if (room == null) {
            throw new RuntimeException("Room not found");
        }
        reservation.setRoom(room);

        Nurse nurse = nurseService.getNurseById(UUID.fromString(dto.getNurseId().toString()));
        if (nurse == null) {
            throw new RuntimeException("Nurse not found");
        }
        reservation.setNurse(nurse);

        // Calculate total fee based on room price and duration
        long days = ChronoUnit.DAYS.between(dto.getDateIn(), dto.getDateOut());
        double roomPrice = room.getPricePerDay();
        reservation.setTotalFee(days * roomPrice);

        // Set default values
        reservation.setDeleted(false);
        
        // Decrease room quota
        roomService.updateQuota(room.getId(), -1);

        return reservationDb.save(reservation);
    }

    @Override
    public boolean validateReservationDates(LocalDate dateIn, LocalDate dateOut) {
        if (dateIn == null || dateOut == null) {
            return false;
        }
        
        LocalDate today = LocalDate.now();
        return !dateIn.isBefore(today) && 
               !dateOut.isBefore(dateIn);
    }
    
    @Override
    public Reservation getReservationById(String id) {
        return reservationDb.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

   
}