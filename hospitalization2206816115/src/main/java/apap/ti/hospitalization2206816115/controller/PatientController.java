package apap.ti.hospitalization2206816115.controller;

import apap.ti.hospitalization2206816115.dto.request.AddPatientRequestDTO;
import apap.ti.hospitalization2206816115.model.Patient;
import apap.ti.hospitalization2206816115.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/search")
    public String searchPatientForm(Model model) {
        model.addAttribute("nik", "");
        return "search-patient";
    }

    @PostMapping("/search")
    public String searchPatient(@RequestParam("nik") String nik, Model model) {
        Optional<Patient> patient = patientService.getPatientByNik(nik);
        if (patient.isPresent()) {
            model.addAttribute("patient", patient.get());
            return "patient-found";
        } else {
            return "patient-not-found";
        }
    }

    @GetMapping("/create")
    public String createPatientForm(Model model) {
        model.addAttribute("patient", new AddPatientRequestDTO());
        return "form-add-patient"; // Ini merender halaman form
    }

    @PostMapping("/create")
    public String createPatient(@Valid @ModelAttribute("patient") AddPatientRequestDTO patientDTO, 
                                BindingResult bindingResult, 
                                RedirectAttributes redirectAttributes, 
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "form-add-patient"; // Jika ada error validasi, kembali ke form
        }

        try {
            // Konversi DTO menjadi entitas dan simpan di database melalui service
            Patient patient = patientService.createPatient(patientDTO);

            // Redirect ke halaman sukses dengan pesan sukses
            redirectAttributes.addFlashAttribute("responseMessage", "Patient created successfully.");
            return "redirect:/patients/success"; // Mengarahkan ke halaman sukses
        } catch (IllegalArgumentException e) {
            // Jika ada error, tampilkan pesan error di halaman create
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients/create"; // Kembali ke form dengan pesan error
        }
    }

    @GetMapping("/success")
    public String successPage(Model model) {
        // Menampilkan halaman sukses dengan pesan yang disimpan
        String responseMessage = (String) model.asMap().get("responseMessage");
        model.addAttribute("responseMessage", responseMessage != null ? responseMessage : "Operation successful.");
        return "response-patient"; // Merender halaman sukses
    }
}