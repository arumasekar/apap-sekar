// NurseService.java
package apap.ti.hospitalization2206816115.service;

import apap.ti.hospitalization2206816115.dto.request.AddNurseRequestDTO;
import apap.ti.hospitalization2206816115.model.Nurse;
import java.util.List;
import java.util.UUID;

public interface NurseService {
    Nurse createNurse(AddNurseRequestDTO nurseDTO);
    Nurse getNurseById(UUID id);
    List<Nurse> getAllNurses();

}