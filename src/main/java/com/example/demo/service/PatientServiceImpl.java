package com.example.demo.service;

import com.example.demo.dto.PatientRequestDTO;
import com.example.demo.dto.PatientResponseDTO;
import com.example.demo.dto.VitalSignsDTO;
import com.example.demo.entity.Patient;
import com.example.demo.entity.VitalSigns;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.VitalSignsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final VitalSignsRepository vitalSignsRepository;

    @Override
    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO dto) {
        Patient patient = PatientMapper.toEntity(dto);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.toResponseDTO(savedPatient);
    }

    @Override
    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patient.setName(dto.getName());
        patient.setParentGuardian(dto.getParentGuardian());
        patient.setGender(dto.getGender());
        patient.setMaritalStatus(dto.getMaritalStatus());
        patient.setOccupation(dto.getOccupation());
        patient.setBirthDate(dto.getBirthDate());
        patient.setBloodType(dto.getBloodType());
        patient.setAllergies(dto.getAllergies());
        patient.setCurrentMedication(dto.getCurrentMedication());
        patient.setPhone(dto.getPhone());
        patient.setEmail(dto.getEmail());
        patient.setAddress(dto.getAddress());
        if (dto.getStatus() != null) {
            patient.setStatus(dto.getStatus());
        }

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toResponseDTO(updatedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return PatientMapper.toResponseDTO(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponseDTO> getPatients(String search, Pageable pageable) {
        Page<Patient> patients;
        if (search == null || search.trim().isEmpty()) {
            patients = patientRepository.findAll(pageable);
        } else {
            patients = patientRepository.findByNameContainingIgnoreCaseOrPhoneContaining(search, search, pageable);
        }
        return patients.map(PatientMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        patientRepository.delete(patient);
    }

    @Override
    @Transactional
    public PatientResponseDTO updateVitalSigns(Long id, VitalSignsDTO vitalSignsDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        VitalSigns vitalSigns = patient.getVitalSigns();
        if (vitalSigns == null) {
            vitalSigns = VitalSigns.builder()
                    .patient(patient)
                    .recordedAt(LocalDateTime.now())
                    .build();
        }

        vitalSigns.setBloodPressure(vitalSignsDTO.getBloodPressure());
        vitalSigns.setWeight(vitalSignsDTO.getWeight());
        vitalSigns.setHeight(vitalSignsDTO.getHeight());
        vitalSigns.setTemperature(vitalSignsDTO.getTemperature());
        vitalSigns.setRecordedAt(LocalDateTime.now());

        vitalSignsRepository.save(vitalSigns);
        patient.setVitalSigns(vitalSigns);

        return PatientMapper.toResponseDTO(patient);
    }
}
