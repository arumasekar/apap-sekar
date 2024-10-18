package apap.ti.hospitalization2206816115.controller;

import apap.ti.hospitalization2206816115.dto.request.AddReservationRequestDTO;
import apap.ti.hospitalization2206816115.model.Reservation;
import apap.ti.hospitalization2206816115.model.Room;
import apap.ti.hospitalization2206816115.model.Nurse;
import apap.ti.hospitalization2206816115.service.ReservationService;
import apap.ti.hospitalization2206816115.service.RoomService;
import apap.ti.hospitalization2206816115.service.FacilityService;
import apap.ti.hospitalization2206816115.service.NurseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private FacilityService facilityService;

    @GetMapping("/create")
    public String createReservationForm(Model model) {
        var reservationDTO = new AddReservationRequestDTO();
        List<Nurse> nurses = nurseService.getAllNurses();
        model.addAttribute("reservationDTO", reservationDTO);
        model.addAttribute("nurses", nurses);
        return "form-add-reservation";
    }

    @PostMapping("/create")
    public String createReservation(
            @Valid @ModelAttribute AddReservationRequestDTO reservationDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            List<Nurse> nurses = nurseService.getAllNurses();
            model.addAttribute("nurses", nurses);
            return "form-add-reservation";
        }
        
        if (!reservationService.validateReservationDates(reservationDTO.getDateIn(), reservationDTO.getDateOut())) {
            result.rejectValue("dateOut", "error.dates", "Invalid date range");
            List<Nurse> nurses = nurseService.getAllNurses();
            model.addAttribute("nurses", nurses);
            return "form-add-reservation";
        }

        try {
            Reservation reservation = reservationService.createReservationFromDTO(reservationDTO);
            return "redirect:/reservations";
        } catch (Exception e) {
            result.rejectValue("roomId", "error.room", "Error creating reservation: " + e.getMessage());
            List<Nurse> nurses = nurseService.getAllNurses();
            model.addAttribute("nurses", nurses);
            return "form-add-reservation";
        }

    }

    @GetMapping("/search-rooms")
    @ResponseBody
    public List<Room> searchRooms(@RequestParam LocalDate dateIn, @RequestParam LocalDate dateOut) {
        return roomService.getAvailableRooms(dateIn, dateOut);
    }


    
}