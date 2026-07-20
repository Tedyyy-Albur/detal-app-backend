package com.example.demo.dto;

import com.example.demo.entity.Gender;
import com.example.demo.entity.PatientStatus;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRequestDTO {

    @NotBlank(message = "Patient name is required")
    private String name;

    private String parentGuardian;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private String maritalStatus;

    private String occupation;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private String bloodType;

    private String allergies;

    private String currentMedication;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @Email(message = "Email must be valid")
    private String email;

    private String address;

    private PatientStatus status;

    private VitalSignsDTO vitalSigns;
}
