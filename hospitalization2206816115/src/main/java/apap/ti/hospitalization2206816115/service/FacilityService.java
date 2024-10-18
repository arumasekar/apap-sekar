package apap.ti.hospitalization2206816115.service;

import java.util.List;
import org.hibernate.validator.constraints.UUID;

import apap.ti.hospitalization2206816115.dto.request.AddFacilityRequestDTO;
import apap.ti.hospitalization2206816115.model.Facility;

public interface FacilityService {
    Facility createFacility(AddFacilityRequestDTO request);
    List<Facility> getAllActiveFacilities();
    
}
