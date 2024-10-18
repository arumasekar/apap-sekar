// FacilityController.java
package apap.ti.hospitalization2206816115.controller;

import apap.ti.hospitalization2206816115.dto.request.AddFacilityRequestDTO;
import apap.ti.hospitalization2206816115.model.Facility;
import apap.ti.hospitalization2206816115.service.FacilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/facility")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @GetMapping("/add")
    public String addFacilityForm(Model model) {
        model.addAttribute("facilityDTO", new AddFacilityRequestDTO());
        return "form-add-facility";
    }

    @PostMapping("/add")
    public String addFacilitySubmit(
            @Valid @ModelAttribute AddFacilityRequestDTO facilityDTO,
            Model model) {
       facilityService.createFacility(facilityDTO); // atau bisa disesuaikan dengan user yang login
       model.addAttribute("responseMessage",
       String.format("Fasilitas berhasil ditambahkan."));
        return "response-facility";
    }

    @GetMapping("/viewall")
    public String listFacility(Model model) {
        List<Facility> listFacility = facilityService.getAllActiveFacilities();
        model.addAttribute("listFacility", listFacility);
        return "viewall-facility";
    }
}