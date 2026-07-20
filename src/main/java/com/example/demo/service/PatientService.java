package com.example.demo.service;

import com.example.demo.dto.PatientRequestDTO;
import com.example.demo.dto.PatientResponseDTO;
import com.example.demo.dto.VitalSignsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    PatientResponseDTO createPatient(PatientRequestDTO dto);
    PatientResponseDTO updatePatient(Long id, PatientRequestDTO dto);
    PatientResponseDTO getPatientById(Long id);
    Page<PatientResponseDTO> getPatients(String search, Pageable pageable);
    void deletePatient(Long id);
    PatientResponseDTO updateVitalSigns(Long id, VitalSignsDTO vitalSignsDTO);
}
