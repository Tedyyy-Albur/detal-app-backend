package com.example.demo.dto;

import com.example.demo.entity.AppointmentStatus;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequestDTO {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private Long serviceId; // Can be null if not defined yet

    @NotNull(message = "Appointment date is required")
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required")
    private LocalTime appointmentTime;

    private AppointmentStatus status; // defaults to SCHEDULED

    private String notes;

    private String consultationNotes;

    private BigDecimal extraCharge;

    private String extraChargeDescription;

    private List<AppointmentExtraChargeDTO> extraCharges;

    private String dentalReason;
    private Boolean hasAllergies;
    private String allergiesDetail;
    private Boolean hasSelfMedicated;
    private String selfMedicatedDetail;
    private String medicationsList;
}
