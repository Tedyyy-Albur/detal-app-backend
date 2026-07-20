package com.example.demo.mapper;

import com.example.demo.dto.PatientRequestDTO;
import com.example.demo.dto.PatientResponseDTO;
import com.example.demo.dto.VitalSignsDTO;
import com.example.demo.entity.Patient;
import com.example.demo.entity.PatientStatus;
import com.example.demo.entity.VitalSigns;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientMapper {

    public static PatientResponseDTO toResponseDTO(Patient patient) {
        if (patient == null) {
            return null;
        }

        VitalSignsDTO vitalSignsDTO = null;
        if (patient.getVitalSigns() != null) {
            vitalSignsDTO = VitalSignsDTO.builder()
                    .id(patient.getVitalSigns().getId())
                    .bloodPressure(patient.getVitalSigns().getBloodPressure())
                    .weight(patient.getVitalSigns().getWeight())
                    .height(patient.getVitalSigns().getHeight())
                    .temperature(patient.getVitalSigns().getTemperature())
                    .recordedAt(patient.getVitalSigns().getRecordedAt())
                    .build();
        }

        return PatientResponseDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .parentGuardian(patient.getParentGuardian())
                .gender(patient.getGender())
                .maritalStatus(patient.getMaritalStatus())
                .occupation(patient.getOccupation())
                .birthDate(patient.getBirthDate())
                .age(patient.getAge())
                .bloodType(patient.getBloodType())
                .allergies(patient.getAllergies())
                .currentMedication(patient.getCurrentMedication())
                .phone(patient.getPhone())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .registrationDate(patient.getRegistrationDate())
                .status(patient.getStatus())
                .vitalSigns(vitalSignsDTO)
                .build();
    }

    public static Patient toEntity(PatientRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Patient patient = Patient.builder()
                .name(dto.getName())
                .parentGuardian(dto.getParentGuardian())
                .gender(dto.getGender())
                .maritalStatus(dto.getMaritalStatus())
                .occupation(dto.getOccupation())
                .birthDate(dto.getBirthDate())
                .bloodType(dto.getBloodType())
                .allergies(dto.getAllergies())
                .currentMedication(dto.getCurrentMedication())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .registrationDate(LocalDate.now())
                .status(dto.getStatus() != null ? dto.getStatus() : PatientStatus.ACTIVE)
                .build();

        if (dto.getVitalSigns() != null) {
            VitalSigns vitalSigns = VitalSigns.builder()
                    .patient(patient)
                    .bloodPressure(dto.getVitalSigns().getBloodPressure())
                    .weight(dto.getVitalSigns().getWeight())
                    .height(dto.getVitalSigns().getHeight())
                    .temperature(dto.getVitalSigns().getTemperature())
                    .recordedAt(LocalDateTime.now())
                    .build();
            patient.setVitalSigns(vitalSigns);
        }

        return patient;
    }
}
