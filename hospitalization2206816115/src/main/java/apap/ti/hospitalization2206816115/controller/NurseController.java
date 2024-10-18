package apap.ti.hospitalization2206816115.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import apap.ti.hospitalization2206816115.service.NurseService;
import apap.ti.hospitalization2206816115.dto.request.AddNurseRequestDTO;


@Controller
public class NurseController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NurseService nurseService;

    @GetMapping("/")
    public String home(Model model) {
        // Query untuk mendapatkan jumlah Reservations
        long reservationCount = (long) entityManager.createQuery("SELECT COUNT(r) FROM Reservation r").getSingleResult();

        // Query untuk mendapatkan jumlah Rooms
        long roomCount = (long) entityManager.createQuery("SELECT COUNT(ro) FROM Room ro").getSingleResult();

        // Query untuk mendapatkan jumlah Patients
        long patientCount = (long) entityManager.createQuery("SELECT COUNT(p) FROM Patient p").getSingleResult();

        // Tambahkan atribut ke model untuk dikirim ke view
        model.addAttribute("reservationCount", reservationCount);
        model.addAttribute("roomCount", roomCount);
        model.addAttribute("patientCount", patientCount);

        return "home";  // Pastikan ini sesuai dengan nama file HTML kamu
    }

    @GetMapping("nurses/add")
    public String addNurseForm(Model model) {
        model.addAttribute("nurseDTO", new AddNurseRequestDTO());
        return "form-add-nurse";
    }

    @PostMapping("nurses/add")
    public String addNurseSubmit(
            @Valid @ModelAttribute AddNurseRequestDTO nurseDTO,
            BindingResult bindingResult,
            Model model) {
        
        if (bindingResult.hasErrors()) {
            return "form-add-nurse";
        }

        nurseService.createNurse(nurseDTO);
         model.addAttribute("responseMessage",
            String.format("Perawat berhasil ditambahkan."));
        return "response-nurse";
    }

    @GetMapping("nurse/viewall")
    public String listNurse(Model model) {
        model.addAttribute("listNurse", nurseService.getAllNurses());
        return "viewall-nurse";
    }
}