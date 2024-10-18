package apap.ti.hospitalization2206816115.service;

import apap.ti.hospitalization2206816115.dto.request.AddPatientRequestDTO;
import apap.ti.hospitalization2206816115.model.Patient;
import apap.ti.hospitalization2206816115.repository.PatientDb;
import apap.ti.hospitalization2206816115.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDb patientDb;

    @Override
    public Optional<Patient> getPatientByNik(String nik) {
        return patientDb.findByNik(nik);
    }

    @Override
    @Transactional
    public Patient createPatient(AddPatientRequestDTO patientDTO) throws IllegalArgumentException {
        if (patientDb.existsByNik(patientDTO.getNik())) {
            throw new IllegalArgumentException("A patient with this NIK already exists.");
        }
        if (patientDb.existsByEmail(patientDTO.getEmail())) {
            throw new IllegalArgumentException("A patient with this email already exists.");
        }

        Patient patient = new Patient();
        patient.setNik(patientDTO.getNik());
        patient.setName(patientDTO.getName());
        patient.setEmail(patientDTO.getEmail());
        patient.setGender(patientDTO.getGender());
        patient.setBirthDate(patientDTO.getBirthDate());
        return patientDb.save(patient);
    }
}