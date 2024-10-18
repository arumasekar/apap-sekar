// FacilityService.java
package apap.ti.hospitalization2206816115.service;

import apap.ti.hospitalization2206816115.dto.request.AddFacilityRequestDTO;
import apap.ti.hospitalization2206816115.model.Facility;
import apap.ti.hospitalization2206816115.repository.FacilityDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class FacilityServiceImpl implements FacilityService{
    @Autowired
    private FacilityDb facilityRepository;

    public Facility createFacility(AddFacilityRequestDTO request) {
        Facility facility = new Facility();
        facility.setId(UUID.randomUUID());
        facility.setName(request.getName());
        facility.setFee(request.getFee());

        facility.setCreatedDate(LocalDateTime.now());
        facility.setDeleted(false);
        
        return facilityRepository.save(facility);
    }

    public List<Facility> getAllActiveFacilities() {
        return facilityRepository.findByIsDeletedFalse();
    }
    
}