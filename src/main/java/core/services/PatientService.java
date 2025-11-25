package core.services;

import api.dto.*;
import core.enums.UserType;
import core.mappers.DTOMapper;
import data.entities.Patient;
import data.repositories.ConditionRepository;
import data.repositories.EncounterRepository;
import data.repositories.ObservationRepository;
import data.repositories.PatientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class PatientService {

    @Inject
    PatientRepository patientRepository;

    public List<PatientDTO> getAllPatients(int pageIndex, int pageSize, boolean eager) {
        List<Patient> patients = eager
                ? patientRepository.findAllPatientsWithRelations(pageIndex, pageSize)
                : patientRepository.findAllPatients(pageIndex, pageSize);
        return patients.stream()
                .map(p -> DTOMapper.toPatientDTO(p, eager))
                .collect(Collectors.toList());
    }

    /**
     * Get patient by ID, optionally fetch relations
     */
    public PatientDTO getPatientById(UUID patientId, boolean eager) {
        Patient patient = eager
                ? patientRepository.findByIdWithRelations(patientId)
                : patientRepository.findById(patientId);

        if (patient == null) {
            throw new IllegalArgumentException("Patient not found");
        }

        return DTOMapper.toPatientDTO(patient, eager);
    }

    /**
     * Get patient by email
     */
    public PatientDTO getPatientByEmail(String email, boolean eager) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        Patient patient = eager
                ? patientRepository.findByEmailWithRelations(email)
                : patientRepository.findByEmail(email);

        if (patient == null) return null;

        return DTOMapper.toPatientDTO(patient, eager);
    }

    /**
     * Search patients by name (partial match)
     */
    public List<PatientDTO> searchPatientsByName(String namePattern, int pageIndex, int pageSize, boolean eager) {
        if (namePattern == null || namePattern.isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }

        List<Patient> patients = eager
                ? patientRepository.searchByNameWithRelations(namePattern, pageIndex, pageSize)
                : patientRepository.searchByName(namePattern, pageIndex, pageSize);

        return patients.stream()
                .map(p -> DTOMapper.toPatientDTO(p, eager))
                .collect(Collectors.toList());
    }
    public long countPatients() {
        return patientRepository.count();
    }

}
