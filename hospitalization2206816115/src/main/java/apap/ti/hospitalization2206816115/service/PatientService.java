package apap.ti.hospitalization2206816115.service;

import apap.ti.hospitalization2206816115.dto.request.AddPatientRequestDTO;
import apap.ti.hospitalization2206816115.model.Patient;

import java.util.Optional;

public interface PatientService {
    Optional<Patient> getPatientByNik(String nik);
    Patient createPatient(AddPatientRequestDTO patientDTO) throws IllegalArgumentException;
}