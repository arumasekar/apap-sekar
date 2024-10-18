package apap.ti.hospitalization2206816115.service;

import apap.ti.hospitalization2206816115.dto.request.AddNurseRequestDTO;
import apap.ti.hospitalization2206816115.model.Nurse;
import apap.ti.hospitalization2206816115.repository.NurseDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class NurseServiceImpl implements NurseService {

    @Autowired
    private NurseDb nurseDb;

    @Override
    public Nurse createNurse(AddNurseRequestDTO nurseDTO) {
        var nurse = new Nurse();
        nurse.setId(UUID.randomUUID());
        nurse.setName(nurseDTO.getName());
        nurse.setEmail(nurseDTO.getEmail());
        nurse.setGender(convertGender(nurseDTO.getGender()));
        nurse.setDeleted(false);
        return nurseDb.save(nurse);
    }

    @Override
    public List<Nurse> getAllNurses() {
        return nurseDb.findAllByIsDeletedFalse();
    }

    @Override
    public Nurse getNurseById(UUID id) {
        Optional<Nurse> nurse = nurseDb.findByIdAndIsDeletedFalse(id);
        return nurse.orElse(null);
    }
    
    private boolean convertGender(String gender) {
        return switch (gender.toUpperCase()) {
            case "L", "TRUE", "1" -> true;
            case "P", "FALSE", "0" -> false;
            default -> throw new IllegalArgumentException("Invalid gender format");
        };
    }
}