package com.example.demo.dto;

import com.example.demo.entity.Gender;
import com.example.demo.entity.PatientStatus;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponseDTO {
    private Long id;
    private String name;
    private String parentGuardian;
    private Gender gender;
    private String maritalStatus;
    private String occupation;
    private LocalDate birthDate;
    private int age; // calculated
    private String bloodType;
    private String allergies;
    private String currentMedication;
    private String phone;
    private String email;
    private String address;
    private LocalDate registrationDate;
    private PatientStatus status;
    private VitalSignsDTO vitalSigns;
}
