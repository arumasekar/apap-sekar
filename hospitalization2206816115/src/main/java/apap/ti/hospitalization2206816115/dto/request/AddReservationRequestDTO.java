package apap.ti.hospitalization2206816115.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AddReservationRequestDTO {
    @NotNull
    private LocalDate dateIn;

    @NotNull
    private LocalDate dateOut;

    @NotNull
    private Long nurseId;

    @NotNull
    private Long roomId;

    // Getters and setters
    public LocalDate getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateOut) {
        this.dateOut = dateOut;
    }

    public Long getNurseId() {
        return nurseId;
    }

    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}