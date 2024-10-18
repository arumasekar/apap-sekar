package apap.ti.hospitalization2206816115.service;

import apap.ti.hospitalization2206816115.dto.request.AddReservationRequestDTO;
import apap.ti.hospitalization2206816115.model.Reservation;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    Reservation createReservationFromDTO(AddReservationRequestDTO dto);
    boolean validateReservationDates(LocalDate dateIn, LocalDate dateOut);
    Reservation getReservationById(String id);
}